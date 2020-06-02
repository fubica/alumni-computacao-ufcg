FROM openjdk:8

ARG BRANCH

# Install.
RUN \
  sed -i 's/# \(.*multiverse$\)/\1/g' /etc/apt/sources.list && \
  apt-get update -y && \
  apt-get upgrade -y && \
  apt-get install -y build-essential && \
  apt-get install -y software-properties-common && \
  apt-get install -y byobu curl git htop man unzip vim wget maven && \
  apt-get install -y net-tools iputils-ping && \
  rm -rf /var/lib/apt/lists/*

# Set environment variables.
ENV HOME /root

# Define working directory.
WORKDIR /root

# Installing Alumni
RUN \
  git clone https://github.com/fubica/alumni-computacao-ufcg.git && \
  (cd alumni-computacao-ufcg && git checkout $BRANCH && mvn install -Dmaven.test.skip=true)

# Define working directory.
WORKDIR /root/alumni-computacao-ufcg

RUN \
  mvn dependency:sources

CMD "./mvnw spring-boot:run -X > log.out 2> log.err"

