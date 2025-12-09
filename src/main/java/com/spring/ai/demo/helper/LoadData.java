    package com.spring.ai.demo.helper;

    import io.qdrant.client.QdrantClient;
    import jakarta.annotation.PostConstruct;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.ai.document.Document;
    import org.springframework.ai.reader.tika.TikaDocumentReader;
    import org.springframework.ai.transformer.splitter.TextSplitter;
    import org.springframework.ai.transformer.splitter.TokenTextSplitter;
    import org.springframework.ai.vectorstore.VectorStore;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.core.io.Resource;
    import org.springframework.stereotype.Component;

    import java.util.*;
    import java.util.concurrent.ExecutionException;

    @Component
    public class LoadData {

        private static final Logger logger = LoggerFactory.getLogger(LoadData.class);

        @Value("classpath:Service_Agreement.pdf")
        Resource serviceAgreement;
        @Value("classpath:Case_Summary.pdf")
        Resource caseSummary;
        @Value("classpath:Employee_Data_Privacy_Policy.pdf")
        Resource employeeDataPrivacyPolicy;
        @Value("classpath:Strategy_Memo.pdf")
        Resource strategyMemo;
        @Value("classpath:Vendor_Contract_Amendment.pdf")
        Resource vendorContractAmendment;
        @Value("${spring.ai.vectorstore.qdrant.collection-name}")
        String collectionName;

        private final VectorStore vectorStore;
        private final QdrantClient qdrantClient;

        public LoadData(VectorStore  v, QdrantClient qdrantClient) {
            this.vectorStore = v;
            this.qdrantClient = qdrantClient;
        }

        @PostConstruct
        public void initialize() throws ExecutionException, InterruptedException {
            Long result = qdrantClient.countAsync(collectionName).get();
            if ( result != null && result > 0) {
                logger.info("Qdrant: Data already exists. Skipping ingestion.");
                return;
            }
            logger.info("Qdrant: No data found. Ingesting documents...");
            loadPDFToVectorStore();
        }


        public void loadPDFToVectorStore() {
            List<Resource> documents = List.of(serviceAgreement, caseSummary,
                    employeeDataPrivacyPolicy, strategyMemo , vendorContractAmendment);

            TextSplitter textSplitter =
                    TokenTextSplitter.builder().withChunkSize(200).withMaxNumChunks(400).build();

            for(Resource doc: documents){
                TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(doc);
                List<Document> docs = tikaDocumentReader.get();
                vectorStore.add(textSplitter.split(docs));
                logger.info("Document: {}", doc + "added to Vector Store");
            }
        }
    }
