package org.example.wineprediction;

// import java.util.logging.LogManager;
import org.apache.spark.sql.SparkSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
        public static final Logger logger = LoggerFactory.getLogger(App.class);
        //  rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        // logger.setLevel(Level.ERROR); // Change INFO to the desired log level


        private static final String ACCESS_KEY_ID = "ASIAU6GD2NSI5ZERYJFR"; 
        private static final String SECRET_KEY = "YJ5Vm4fhCW0IWTueG5O5OZuMNE7t6bGF3EJGVR76";

        private static final String MASTER_URI = "local[*]";

        public static void main(String[] args) {

                SparkSession spark = SparkSession.builder()
                                .appName("Wine Quality Prediction App").master(MASTER_URI)
                                .config("spark.executor.memory", "3g")
                                .config("spark.driver.memory", "3g")
                                .config("spark.jars.packages", "org.apache.hadoop:hadoop-aws:3.2.2")
                                .getOrCreate();

                spark.sparkContext().hadoopConfiguration().set("fs.s3a.aws.credentials.provider",
                                "com.amazonaws.auth.InstanceProfileCredentialsProvider,com.amazonaws.auth.DefaultAWSCredentialsProviderChain");
                spark.sparkContext().hadoopConfiguration().set("fs.s3a.access.key", ACCESS_KEY_ID);
                spark.sparkContext().hadoopConfiguration().set("fs.s3a.secret.key", SECRET_KEY);

                LogisticRegressionUtil parser = new LogisticRegressionUtil();
                parser.trainModel(spark);

                spark.stop();
        }
}
