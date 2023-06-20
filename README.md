
Spring Sec 509 cert and https.

openssl req -x509 -sha256 -days 3650 -newkey rsa:4096 -keyout ssecty_rootCA1.key -out ssecty_rootCA1.crt
#changeit
openssl req -new -newkey rsa:4096 -keyout ssecty_localhost1.key -out ssecty_localhost1.csr
#changeit


ssecty_localhost1.ext
authorityKeyIdentifier=keyid,issuer
basicConstraints=CA:FALSE
subjectAltName = @alt_names
[alt_names]
DNS.1 = localhost


openssl x509 -req -CA ssecty_rootCA1.crt -CAkey ssecty_rootCA1.key -in ssecty_localhost1.csr -out ssecty_localhost1.crt -days 365 -CAcreateserial -extfile ssecty_localhost1.ext
openssl x509 -in ssecty_localhost1.crt -text

openssl pkcs12 -export -out ssecty_localhost1.p12 -name "localhost" -inkey ssecty_localhost1.key -in ssecty_localhost1.crt
keytool -importkeystore -srckeystore ssecty_localhost1.p12 -srcstoretype PKCS12 -destkeystore ssecty_keystore1.jks -deststoretype JKS
#changeit


----
MA

keytool -import -trustcacerts -noprompt -alias ca -ext san=dns:localhost,ip:127.0.0.1 -file ssecty_rootCA1.crt -keystore ssecty_truststore1.jks
#changeit

openssl req -new -newkey rsa:4096 -nodes -keyout ssecty_clientBob1.key -out ssecty_clientBob1.csr

openssl x509 -req -CA ssecty_rootCA1.crt -CAkey ssecty_rootCA1.key -in ssecty_clientBob1.csr -out ssecty_clientBob1.crt -days 365 -CAcreateserial
openssl pkcs12 -export -out ssecty_clientBob1.p12 -name "clientBob" -inkey ssecty_clientBob1.key -in ssecty_clientBob1.crt


#https://www.baeldung.com/x-509-authentication-in-spring-security#:~:text=509%20authentication%20in%20our%20Spring,create%20a%20server%2Dside%20certificate.&text=Similarly%2C%20as%20for%20the%20CA,create%20a%20configuration%20file%20%E2%80%93%20localhost.

