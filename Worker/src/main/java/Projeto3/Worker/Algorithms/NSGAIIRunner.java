package Projeto3.Worker.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.lab.experiment.Experiment;
import org.uma.jmetal.lab.experiment.ExperimentBuilder;
import org.uma.jmetal.lab.experiment.component.impl.ExecuteAlgorithms;
import org.uma.jmetal.lab.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.lab.experiment.util.ExperimentProblem;
import org.uma.jmetal.operator.crossover.impl.IntegerSBXCrossover;
import org.uma.jmetal.operator.mutation.impl.IntegerPolynomialMutation;
import org.uma.jmetal.qualityindicator.impl.Epsilon;
import org.uma.jmetal.qualityindicator.impl.InvertedGenerationalDistancePlus;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

import Projeto3.Worker.Models.Lecture;
import Projeto3.Worker.Models.ProblemConfiguration;
import Projeto3.Worker.Models.Room;

public class NSGAIIRunner {

	private static final String name = "nsgaii";
	private static final int INDEPENDENT_RUNS = 1;
	private List<Lecture> lectures;
	private List<Room> rooms;

	public NSGAIIRunner(List<Lecture> lectures, List<Room> rooms) {
		this.lectures = lectures;
		this.rooms = rooms;
	}

	public static String getName() {
		return name;
	}

	public void runAlg() {
		System.out.println(1);

		String experimentBaseDirectory = "RESULTADOS";
		List<ExperimentProblem<IntegerSolution>> problemList = new ArrayList<>();
		problemList.add(new ExperimentProblem<>(new ProblemConfiguration(lectures, rooms)));
		List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> algorithmList =

				configureAlgorithmList(problemList);
		Experiment<IntegerSolution, List<IntegerSolution>> experiment =

				new ExperimentBuilder<IntegerSolution, List<IntegerSolution>>("NSGAIIStudy")

						.setAlgorithmList(algorithmList)

						.setProblemList(problemList)

						.setExperimentBaseDirectory(experimentBaseDirectory)

						.setOutputParetoFrontFileName("FUN")

						.setOutputParetoSetFileName("VAR")

						.setReferenceFrontDirectory("resources/referenceFrontsCSV")

						.setIndicatorList(

								Arrays.asList(

										new Epsilon(),

										new InvertedGenerationalDistancePlus()))

						.setIndependentRuns(INDEPENDENT_RUNS)

						.setNumberOfCores(8)

						.build();
		System.out.println(2);
		System.out.println("fim");
		new ExecuteAlgorithms<>(experiment).run();

	}

	static List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> configureAlgorithmList(

			List<ExperimentProblem<IntegerSolution>> problemList) {

		List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> algorithms = new ArrayList<>();
		System.out.println(4);
		for (int run = 0; run < INDEPENDENT_RUNS; run++) {

			for (int i = 0; i < problemList.size(); i++) {

				Algorithm<List<IntegerSolution>> algorithm =

//						new NSGAIIBuilder<>(
//
//								problemList.get(i).getProblem(),
//
//								new SBXCrossover(1.0, 5), //
//
//								new PolynomialMutation(
//
//										1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0),
//
//								100)
//
//										.setMaxEvaluations(2500)
//
//										.build();

						new NSGAIIBuilder<>(problemList.get(i).getProblem(), new IntegerSBXCrossover(1.0, 5),
								new IntegerPolynomialMutation(
										1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0),
								100).setMaxEvaluations(5).build();

				algorithms.add(new ExperimentAlgorithm<>(algorithm, "NSGAII", problemList.get(i), run));

			}

		}
		return algorithms;

	}

}
