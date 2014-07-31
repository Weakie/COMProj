#include "com_weakie_jni_SendData.h"
#include "SendData.h"

JNIEXPORT jboolean JNICALL Java_com_weakie_jni_SendData_initCOM
(JNIEnv *env, jclass cls, jobject obj){
	//get fieldId
	jclass ptrCls = env->GetObjectClass(obj);
	jfieldID pHComFid = env->GetFieldID(ptrCls,"pHCom","J");
	jfieldID pWrOverlandFid = env->GetFieldID(ptrCls,"pWrOverland","J");

	//initCom
	HANDLE* g_hCom = new HANDLE();
	OVERLAPPED* g_wrOverland = new OVERLAPPED();
	bool result = InitCom(*g_hCom,*g_wrOverland);

	//convert the ptr to long value
	long long g_hComResult = (long long)g_hCom;
	long long g_wrOverlandResult = (long long)g_wrOverland;

	//set the field value in object
	env->SetLongField(obj,pHComFid,g_hComResult);
	env->SetLongField(obj,pWrOverlandFid,g_wrOverlandResult);

	return result;
}

JNIEXPORT jboolean JNICALL Java_com_weakie_jni_SendData_sendData
(JNIEnv *env, jclass cls, jobject obj, jbyteArray array){
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
	jbyte buffer[32];
	env->GetByteArrayRegion(array,0,32,buffer);

	char buf[32];
	for(int i=0;i<32;i++){
		buf[i] = buffer[i];
	}
	//send data
	bool result = SendData(*g_hCom,*g_wrOverland,buf,sizeof(buf));
	
	return result;
}


JNIEXPORT void JNICALL Java_com_weakie_jni_SendData_formatData
(JNIEnv * env, jclass cls, jint x, jint y, jint z, jint sx, jint sy, jint sz, jbyteArray buffer){
	Speed speed(sx,sy,sz);
	Point3i point(x,y,z);

	char buf[32];
	FormateData(point,speed,buf);
	jbyte b[32];
	for(int i=0;i<32;i++){
		b[i] = buf[i];
	}
	env->SetByteArrayRegion(buffer,0,32,b);
}