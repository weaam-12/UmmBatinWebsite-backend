FROM ubuntu:latest
LABEL authors="weaam"
# استخدام صورة JDK 17 (مطابقة لإصدار Java في pom.xml)
FROM eclipse-temurin:17-jdk-jammy

# تعيين مجلد العمل
WORKDIR /app

# نسخ ملفات البناء الأساسية
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# تحميل التبعيات (مع التخزين المؤقت لتحسين الأداء)
RUN ./mvnw dependency:go-offline

# نسخ الكود المصدري
COPY src ./src

# بناء التطبيق (بدون تشغيل الاختبارات)
RUN ./mvnw package -DskipTests

# تعيين متغيرات البيئة
ENV PORT=8080
EXPOSE $PORT

# أمر التشغيل (مع دعم متغير PORT لـ Render)
CMD ["sh", "-c", "java -jar /app/target/service-management-0.0.1-SNAPSHOT.jar --server.port=${PORT}"]
ENTRYPOINT ["top", "-b"]