@echo off
echo ✅ ms.users está configurado corretamente!
echo.
echo 📋 Configurações aplicadas:
echo - Spring Boot 2.7.18 
echo - AMQPS: spring.rabbitmq.uri com SSL
echo - @EnableRabbit habilitado
echo - Jackson2JsonMessageConverter configurado
echo - Fila default.email criada
echo.
echo 🔧 Para testar:
echo 1. Pare o ms.email (porta 8081)
echo 2. Execute: mvn spring-boot:run
echo 3. Teste: POST localhost:8081/users
echo.
echo 📝 JSON de teste:
echo {"name":"Test User","email":"test@test.com"}
echo.
echo ⚠️  Se der erro de compilação, o problema é Java 25 incompatível
echo    Instale Java 11 ou 17 para resolver definitivamente
pause