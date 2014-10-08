#include "com_weakie_share_jni_SendData.h"
#include "SendData.h"

const int MAX_BUFFER_SIZE = 128;

JNIEXPORT jboolean JNICALL Java_com_weakie_share_jni_SendData_initCOM
(JNIEnv *env, jclass cls, jobject obj,jstring port){
	//get fieldId
	jclass ptrCls = env->GetObjectClass(obj);
	jfieldID pHComFid = env->GetFieldID(ptrCls,"pHCom","J");
	jfieldID pWrOverlandFid = env->GetFieldID(ptrCls,"pWrOverland","J");

	const jchar* str;
	//str = env->GetStringUTFChars(port,NULL);
	str = env->GetStringChars(port,NULL);
	if(str == NULL){
		cout << "JNI data transform fail in initCOM." << endl;
		return false;
	}
	//initCom
	HANDLE* g_hCom = new HANDLE();
	OVERLAPPED* g_wrOverland = new OVERLAPPED();
	bool result = InitCom(*g_hCom,*g_wrOverland,(const TCHAR*)str);
	//env->ReleaseStringUTFChars(port,str);
	env->ReleaseStringChars(port, str);

	//convert the ptr to long value
	long long g_hComResult = (long long)g_hCom;
	long long g_wrOverlandResult = (long long)g_wrOverland;

	//set the field value in object
	env->SetLongField(obj,pHComFid,g_hComResult);
	env->SetLongField(obj,pWrOverlandFid,g_wrOverlandResult);

	return result;
}

JNIEXPORT jboolean JNICALL Java_com_weakie_share_jni_SendData_sendData
(JNIEnv *env, jclass cls, jobject obj, jbyteArray array, jint bufSize){
	//check buffer size
	int length = env->GetArrayLength(array);
	if (bufSize > MAX_BUFFER_SIZE || bufSize > length){
		return false;
	}

	//get fieldId
	jclass ptrCls = env->GetObjectClass(obj);
	jfieldID pHComFid = env->GetFieldID(ptrCls,"pHCom","J");
	jfieldID pWrOverlandFid = env->GetFieldID(ptrCls,"pWrOverland","J");

	//get field value
	long long g_hComValue = env->GetLongField(obj,pHComFid);
	long long g_wrOverlandValue = env->GetLongField(obj,pWrOverlandFid);

	//convert long to ptr value
	HANDLE* g_hCom = (HANDLE*) g_hComValue;
	OVERLAPPED* g_wrOverland = (OVERLAPPED*) g_wrOverlandValue;

	//get array data of buf
	jbyte buffer[MAX_BUFFER_SIZE];
	env->GetByteArrayRegion(array,0,bufSize,buffer);
	
	//convert jbyte[] to char[] type
	//char buf[MAX_BUFFER_SIZE];
	//for (int i = 0; i<bufSize; i++){
		//buf[i] = buffer[i];
	//}

	//send data
	bool result = SendData(*g_hCom,*g_wrOverland,(char*)buffer,bufSize);

	return result;
}

JNIEXPORT jboolean JNICALL Java_com_weakie_share_jni_SendData_destroy
(JNIEnv *env, jclass cls, jobject obj){
	//get fieldId
	jclass ptrCls = env->GetObjectClass(obj);
	jfieldID pHComFid = env->GetFieldID(ptrCls,"pHCom","J");
	jfieldID pWrOverlandFid = env->GetFieldID(ptrCls,"pWrOverland","J");

	//get field value
	long long g_hComValue = env->GetLongField(obj,pHComFid);
	long long g_wrOverlandValue = env->GetLongField(obj,pWrOverlandFid);

	//convert long to ptr value
	HANDLE* g_hCom = (HANDLE*) g_hComValue;
	OVERLAPPED* g_wrOverland = (OVERLAPPED*) g_wrOverlandValue;

	//TODO release handler


	//delete handler
	delete g_wrOverland;
	delete g_hCom;

	return true;
}

JNIEXPORT jint JNICALL Java_com_weakie_share_jni_SendData_formatPointData
(JNIEnv * env, jclass cls, jint x, jint y, jint z, jint sx, jint sy, jint sz, jint flag, jbyteArray buffer){
	
	//constract object
	Speed speed(sx,sy,sz);
	Point3i point(x,y,z);

	//format the data by parameters and store in a char[]
	char buf[MAX_BUFFER_SIZE];
	int bufLength = FormatePointData(point,speed,buf,flag);
	
	//check buffer array length
	int length = env->GetArrayLength(buffer);
	if(length < bufLength){
		return -1;
	}

	//convert the char[] to jbyte[] buffer
	//jbyte b[MAX_BUFFER_SIZE];
	//for (int i = 0; i<bufLength; i++){
		//b[i] = buf[i];
	//}

	//set the data to the preallocted buf
	env->SetByteArrayRegion(buffer,0,bufLength,(jbyte*)buf);

	return bufLength;
}


JNIEXPORT jint JNICALL Java_com_weakie_share_jni_SendData_formatIniWeldParaData
(JNIEnv * env, jclass cls, jint h0, jint h1, jint h2, jint h3, jint h4, jint h5, jint v0, jint v1, jint v2, jint v3, jint v4, jint v5, jint i1, jint i2, jint i3, jint flag, jbyteArray buffer){
	
	//constract object
	WeldPara wp(h0, h1, h2, h3, h4, h5, v0, v1, v2, v3, v4, v5, i1, i2, i3);

	//format the data by parameters and store in a char[]
	char buf[MAX_BUFFER_SIZE];
	int bufLength = FormateIniWeldPara(wp, buf, flag);

	//check buffer array length
	int length = env->GetArrayLength(buffer);
	if(length < bufLength){
		return -1;
	}

	//convert the char[] to jbyte[] buffer
	//jbyte b[MAX_BUFFER_SIZE];
	//for (int i = 0; i<bufLength; i++){
		//b[i] = buf[i];
	//}
	
	//set the data to the preallocted buf
	env->SetByteArrayRegion(buffer, 0, bufLength, (jbyte*)buf);

	return bufLength;
}