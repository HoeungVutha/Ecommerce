import ch.qos.logback.classic.AsyncAppender
import ch.qos.logback.classic.filter.ThresholdFilter
import ch.qos.logback.core.util.FileSize
import grails.util.BuildSettings
import grails.util.Environment
import org.springframework.boot.logging.logback.ColorConverter
import org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter

import java.nio.charset.Charset

conversionRule 'clr', ColorConverter
conversionRule 'wex', WhitespaceThrowableProxyConverter

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName('UTF-8')

        pattern = '%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} ' + // Date
                '%clr(%5p) ' + // Log level
                '%clr([%X{process_id}]){faint} ' +
//                  '%clr(---){faint} %clr([%15.15t]){faint} ' + // Thread
                '%clr(%-40.40logger{30}){cyan} %clr(:){faint} ' + // Logger
                '%m%n%wex' // Message
    }
}

def targetDir = BuildSettings.TARGET_DIR
if (Environment.isDevelopmentMode() && targetDir != null) {
    appender("FULL_STACKTRACE", FileAppender) {
        file = "${targetDir}/stacktrace.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = "%level %logger - %msg%n"
        }
    }
    logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)
}

def dateFile = new Date().format('yyyy-MM-dd') // current date of file
def LOG_PATH = "logs" // path to write log
def LOG_ARCHIVE = "${LOG_PATH}/archive" // path to append to zip

// write logger file for error file
appender("ROLLING-ERROR", RollingFileAppender) {
    file = "${LOG_PATH}/errorFile.${dateFile}.log"
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS a} %-6p [%t] %c :%L ====== %m%n"
    }
    rollingPolicy(SizeAndTimeBasedRollingPolicy) {
        fileNamePattern = "${LOG_ARCHIVE}/errorFile.%d{yyyy-MM-dd}.%i.zip" //property defines a file name pattern for archived log files
        maxHistory = 60 // each file should be at most 2GB, keep 60 days worth of history, before deleting.
        totalSizeCap = FileSize.valueOf("2GB") // Oldest archives are deleted asynchronously when the total size cap is exceeded
        maxFileSize = FileSize.valueOf("500MB") // Each file should be at most 500MB
    }
    filter(ThresholdFilter){
        level = ERROR // set log level
    }
}

// write logger file for all or info file
appender("ROLLING-INFO", RollingFileAppender) {
    file = "${LOG_PATH}/infoFile.${dateFile}.log"
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS a} %-6p [%t] %c :%L ====== %m%n"
    }
    rollingPolicy(SizeAndTimeBasedRollingPolicy) {
        fileNamePattern = "${LOG_ARCHIVE}/infoFile.%d{yyyy-MM-dd}.%i.zip" //property defines a file name pattern for archived log files
        maxHistory = 60 // each file should be at most 2GB, keep 60 days worth of history, before deleting.
        totalSizeCap = FileSize.valueOf("2GB") // Oldest archives are deleted asynchronously when the total size cap is exceeded
        maxFileSize = FileSize.valueOf("500MB") // Each file should be at most 500MB
    }
    filter(ThresholdFilter){
        level = INFO // set log level
    }
}

// appender ref to file name rolling error
appender("ERROR", AsyncAppender) {
    appenderRef("ROLLING-ERROR")
}
// appender ref to file name rolling info
appender("INFO", AsyncAppender) {
    appenderRef("ROLLING-INFO")
}

logger("tonle-backend", INFO, ["STDOUT"], true)
root(INFO, ['STDOUT', 'ERROR', 'INFO'])
