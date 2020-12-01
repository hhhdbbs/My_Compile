FROM java:7
WORKDIR /app/
COPY ./* ./
RUN javac miniplc0java/App.java
RUN chmod +x miniplc0java/App
