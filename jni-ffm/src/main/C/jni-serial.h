/∗∗
 ∗ Initializes the serial port and returns a java SerialPortConfig objects
 ∗ that contains the hardware address for the serial port, and holds
 ∗ information needed by the serial port such as the next buffer
 ∗ to write data into
 ∗
 ∗ @param env JNI env that can be used by the method
 ∗ @param comPortName the name of the serial port
 ∗ @returns SerialPortConfig object to be passed ot setSerialPortBit
 ∗          and getSerialPortBit calls
 ∗/
jobject initializeSerialPort(JNIEnv∗ env, jobject obj,  jstring comPortName);

/∗∗
 ∗ Sets a single bit in an 8 bit byte to be sent by the serial port
 ∗
 ∗ @param env JNI env that can be used by the method
 ∗ @param serialPortConfig object returned by initializeSerialPort
 ∗ @param whichBit value from 1‑8 indicating which bit to set
 ∗ @param bitValue 0th bit contains bit value to be set
 ∗/
void setSerialPortBit(JNIEnv∗ env, jobject obj, jobject serialPortConfig,
  jint whichBit,  jint bitValue);

/∗∗
 ∗ Gets a single bit in an 8 bit byte read from the serial port
 ∗
 ∗ @param env JNI env that can be used by the method
 ∗ @param serialPortConfig object returned by initializeSerialPort
 ∗ @param whichBit value from 1‑8 indicating which bit to read
 ∗ @returns the bit read in the 0th bit of the jint
 ∗/
jint getSerialPortBit(JNIEnv∗ env, jobject obj, jobject serialPortConfig,
  jint whichBit);

/∗∗
 ∗ Read the next byte from the serial port
 ∗
 ∗ @param env JNI env that can be used by the method
 ∗/
void readNextByte(JNIEnv∗ env, jobject obj);

/∗∗
 ∗ Send the next byte
 ∗
 ∗ @param env JNI env that can be used by the method
 ∗/
void sendNextByte(JNIEnv∗ env, jobject obj);