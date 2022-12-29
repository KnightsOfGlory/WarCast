FROM adoptopenjdk/openjdk11:alpine

# dependencies
RUN apk update && \
    apk upgrade && \
    apk add bash

# runtime
COPY build/distributions/WarCast-1.0.zip /
RUN unzip WarCast-1.0.zip
RUN rm WarCast-1.0.zip

# start
ENTRYPOINT ["/WarCast-1.0/bin/WarCast"]
