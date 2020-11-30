FROM java:7
WORKDIR /app/
COPY ./* ./
RUN javac Compile/src/miniplc0java/App.java
RUN chmod +x Compile/src/miniplc0java/App
