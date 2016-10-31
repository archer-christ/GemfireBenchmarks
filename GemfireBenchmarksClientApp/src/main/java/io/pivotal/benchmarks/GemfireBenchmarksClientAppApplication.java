package io.pivotal.benchmarks;

import io.pivotal.benchmarks.clients.GemfireTransaction;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;



@SpringBootApplication
@ImportResource("classpath:sdg-context.xml")
public class GemfireBenchmarksClientAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GemfireBenchmarksClientAppApplication.class, args);
	}

	@Bean
    CommandLineRunner loadData(final GemfireTransaction gt) {
    	return new CommandLineRunner() {
			@Override
			public void run(String... arg0) throws Exception {

				Scanner input = new Scanner(System.in);;

//				while (true) {
					System.out.println("**********Actions**********");
				    System.out.println("1. Transaction");
				    System.out.println("2. Execute Functions");
				    System.out.println("3. Exit");
				    String option = null;
					try {
							option = input.nextLine();
					} catch (Exception e) {}

					if ( option!=null && option.equals("1")) {
						invokeTransaction();
					} else if ( option!=null && option.equals("2")) {
						invokeFunctions();
					} else if ( option!=null && option.equals("3")) {
						input.close();
						System.exit(0);
					} else {
						input.close();
						System.out.println("Option Not Found. Exiting!");
						System.exit(0);
					}
					input.reset();
//				}

			}

			private void invokeTransaction() {

				System.out.println("Enter the Transaction Count");
				Scanner in = new Scanner(System.in);
				int  transactionCount = in.nextInt();

				System.out.println("**********Starting the Transactions**********");
				String result = gt.executeTransactionUsecase(transactionCount);
				System.out.println("**********Transaction execution completed**********");
				System.out.println(result);
				System.out.println("**********Press Enter To Continue**********");

				in.close();
			}

			private void invokeFunctions() {

				System.out.println("**********Starting Function Execution**********");
				String result = gt.executeTransactionCountFunction("449");
				System.out.println("**********Function execution completed**********");
				System.out.println(result);
				System.out.println("**********Press Enter To Continue**********");

			}

    	};
    }
}
