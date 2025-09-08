/∗∗
 ∗ Initializes the serial port and returns an opaque handle to a native
 ∗ structure that contains the hardware address for the serial port
 ∗ and holds information needed by the serial port such as
 ∗ the next buffer to write data into
 ∗
 ∗ @param env JNI env that can be used by the method
 ∗ @param comPortName the name of the serial port
 ∗ @returns opaque handle to be passed to setSerialPortByte and
 ∗          getSerialPortByte calls
 ∗/
jlong initializeSerialPort2(JNIEnv∗ env, jobject obj, jstring comPortName);

/∗∗
 ∗ sends a byte on the serial port
 ∗
 ∗ @param env JNI env that can be used by the method
 ∗ @param serialPortConfig opaque handle for the serial port
 ∗ @param byte the byte to be sent
 ∗/
void sendSerialPortByte(JNIEnv∗ env, jobject obj, jlong serialPortConfig,
    jbyte byte);

/∗∗
 ∗ Reads the next byte from the serial port
 ∗
 ∗ @param env JNI env that can be used by the method
 ∗ @param serialPortConfig opaque handle for the serial port
 ∗ @returns the byte read from the serial port
 ∗/
jbyte readSerialPortByte(JNIEnv∗ env, jobject obj,  jlong serialPortConfig);