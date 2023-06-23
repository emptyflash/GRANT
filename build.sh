javac -sourcepath lib/TinySound/src/ -d bin src/com/logosstudios/GRANT/*.java
cd bin
jar -c -e com.logosstudios.GRANT.TractorBeam -f GRANT.jar com/logosstudios/GRANT/ kuusisto/tinysound/ ../res/
chmod +x bin/GRANT.jar
