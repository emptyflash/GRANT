#javac -sourcepath lib/TinySound/src/ -d bin src/com/logosstudios/GRANT/*.java
javac -sourcepath lib/TinySound/src/ -d bin src/com/logosstudios/GRANT/*.java
cd bin
# TODO: readd sound
#jar -c -e com.logosstudios.GRANT.TractorBeam -f GRANT.jar com/logosstudios/GRANT/ kuusisto/tinysound/ ../res/
# This worked with java 17, but not 8
#jar -c -e com.logosstudios.GRANT.TractorBeam -f GRANT.jar com/logosstudios/GRANT/ ../res/
jar cfe GRANT.jar com.logosstudios.GRANT.TractorBeam com/logosstudios/GRANT/ kuusisto/tinysound/ ../res/
chmod +x GRANT.jar
python3 ../../cheerpj_2.3/cheerpjfy.py GRANT.jar
