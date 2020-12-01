FROM java:
WORKDIR /app/
COPY ./* ./
RUN javac App.java
RUN chmod +x App
