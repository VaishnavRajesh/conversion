FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD conversion-0.0.1.jar conversion.jar
RUN sh -c 'touch /conversion.jar'

ENTRYPOINT [ "sh", "-c", "java  -jar /conversion.jar" ]