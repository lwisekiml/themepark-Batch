package com.freepass.themepark;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//@EnableBatchProcessing // 배치를 돌리기 위함 (BatchConfig로 따로 분리)
@SpringBootApplication
public class ThemeparkBatchApplication {

	// job을 만들어 줄 수 있는 빌더
	final JobBuilderFactory jobBuilderFactory;

	final StepBuilderFactory stepBuilderFactory;

	public ThemeparkBatchApplication(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}

	@Bean // step 선언
	public Step passStep() {
		return this.stepBuilderFactory.get("passStep") // step의 이름을 적어줌
				.tasklet((stepContribution, chunkContext) -> {
					System.out.println("Execute PassStep");
					return RepeatStatus.FINISHED;
				}).build();
		/*
		return this.stepBuilderFactory.get("passStep")
				.tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
						System.out.println("Execute PassStep");
						return RepeatStatus.FINISHED;
					}
				}).build();
		 */
	}

	@Bean
	public Job passJob() {
		return this.jobBuilderFactory.get("passJob")
				.start(passStep()) // step을 넣을 수 있다.
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ThemeparkBatchApplication.class, args);
	}

}
