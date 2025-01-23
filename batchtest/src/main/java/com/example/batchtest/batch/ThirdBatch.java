package com.example.batchtest.batch;

import com.example.batchtest.entity.PaymentEntity;
import com.example.batchtest.entity.PaymentStatus;
import com.example.batchtest.repository.PaymentRepository;
import jakarta.persistence.EnumType;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ThirdBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final PaymentRepository paymentRepository;


    @Bean
    public Job ThirdJob() {
        return new JobBuilder("ThirdJob", jobRepository)
                .start(paymentStep())
                .build();
    }

    @Bean
    public Step paymentStep() {
        return new StepBuilder("paymentStep", jobRepository)
                .<PaymentEntity, PaymentEntity >chunk(10, platformTransactionManager)
                .reader(paymentReader())
                .processor(paymentProcessor())
                .writer(paymentWriter())
                .build();
    }

    @Bean
    public RepositoryItemReader<PaymentEntity> paymentReader() {
        return new RepositoryItemReaderBuilder<PaymentEntity>()
                .name("paymentReader")
                .pageSize(10)
                .methodName("findByStatus")
                .arguments(PaymentStatus.AWAITING_PAYMENT)
                .repository(paymentRepository)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<PaymentEntity, PaymentEntity> paymentProcessor() {
        return item->{
            item.setStatus(PaymentStatus.SUCCESSED);
            return item;
        };
    }

    @Bean
    public RepositoryItemWriter<PaymentEntity> paymentWriter() {
        return new RepositoryItemWriterBuilder<PaymentEntity>()
                .repository(paymentRepository)
                .methodName("save")
                .build();
    };


}

