#ifndef _SEND_DATA
#define _SEND_DATA

#include  <windows.h>
#include  <stdio.h>
#include  <stdlib.h>
#include  <iostream>
#include  <string>
#include <atlbase.h>

using namespace std;


enum ControlFlag{ ABSOLUTE_POSITION = 1, RELATIVE_POSITION = 3 };

struct Point3i
{
	int x;
	int y;
	int z;
	Point3i(int x, int y, int z) :x(x), y(y), z(z){}
};

struct Speed
{
	int xSpeed; int ySpeed; int zSpeed;

	Speed(int x, int y, int z) : xSpeed(x), ySpeed(y), zSpeed(z){}

};

bool InitCom(HANDLE& m_hCom, OVERLAPPED& wrOverlapped, const TCHAR* port);

bool SendData(HANDLE& m_hCom, OVERLAPPED& wrOverlapped, char* buffer, int bufferSize);

void FormateData(Point3i pt, Speed speed, char* buffer, int flag = ControlFlag::ABSOLUTE_POSITION);

double MoveTime(Point3i start, Point3i end, Speed sp, double delay = 0);

#endif

bool InitCom(HANDLE& m_hCom, OVERLAPPED& wrOverlapped,const TCHAR* port)
{
	//第一步，打开串口
	//HANDLE m_hCom;
	m_hCom = CreateFile(port, GENERIC_READ | GENERIC_WRITE, 0, NULL, OPEN_EXISTING, FILE_FLAG_OVERLAPPED, NULL);
	if (m_hCom == INVALID_HANDLE_VALUE)
	{
		std::cout << "CreateFile fail!" << endl;
		return false;
	}
	//cout << "CreateFile OK!" << endl;

	//第二步，设置缓冲区大小
	if (!SetupComm(m_hCom, 2048, 2048))
	{
		cout << "SetupComm fail! Close Comm!" << endl;
		CloseHandle(m_hCom);
		return false;
	}
	//cout << "SetupComm OK!" << endl;

	//第三步，设置超时
	COMMTIMEOUTS TimeOuts;
	memset(&TimeOuts, 0, sizeof(TimeOuts));
	TimeOuts.ReadIntervalTimeout = MAXDWORD;
	TimeOuts.ReadTotalTimeoutConstant = 0;
	TimeOuts.ReadTotalTimeoutMultiplier = 0;
	TimeOuts.WriteTotalTimeoutConstant = 2000;
	TimeOuts.WriteTotalTimeoutMultiplier = 2000;
	SetCommTimeouts(m_hCom, &TimeOuts);

	//第四步，设置串口参数
	DCB dcb;
	if (!GetCommState(m_hCom, &dcb))
	{
		cout << "GetCommState fail! Comm close" << endl;
		CloseHandle(m_hCom);
		return false;
	}
	//cout << "GetCommState OK!" << endl;

	dcb.DCBlength = sizeof(dcb);
	if (!BuildCommDCB((WCHAR*)_T("9600,n,8,1"), &dcb))//填充DCB的数据传输率、奇偶校验类型、数据位、停止位
	{
		//参数修改错误，进行错误处理
		cout << "BuileCOmmDCB fail,Comm close!" << endl;
		CloseHandle(m_hCom);
		return false;
	}
	if (SetCommState(m_hCom, &dcb))
	{
		//cout << "SetCommState OK!" << endl;
	}

	//第五步，建立并初始化重叠结构
	ZeroMemory(&wrOverlapped, sizeof(wrOverlapped));
	if (wrOverlapped.hEvent != NULL)
	{
		ResetEvent(wrOverlapped.hEvent);
		wrOverlapped.hEvent = CreateEvent(NULL, TRUE, FALSE, NULL);
	}
	return true;
}
bool SendData(HANDLE& m_hCom, OVERLAPPED& wrOverlapped, char* buffer, int bufferSize)
{

	//第七步，发送数据
	//y = 80000(16# 00 01 38 80),VB2004~VB2007,VD2004;
	// send data 30000/80000
	// 10#30000~16# 00 00 75 30
	/*char buffer[16];
	buffer[0] = 0x00;
	buffer[1] = 0x00;
	buffer[2] = 0x00;
	buffer[3] = 0x64;*/
	//cout << "subFunc:" << buffer[0] << endl;

	DWORD dwerrorflag;
	COMSTAT comstat;
	DWORD writenumber = bufferSize;
	DWORD dwhavewrite;

	bool error = WriteFile(m_hCom, buffer, bufferSize, &dwhavewrite, &wrOverlapped);
	return error;
	//// 10#80000~16# 00 01 38 80
	//buffer[4] = 0x00;
	//buffer[5] = 0x01;
	//buffer[6] = 0x38;
	//buffer[7] = 0x80;

	/* 80000/30000
	buffer[8] = 0x00;
	buffer[9] = 0x01;
	buffer[10] = 0x38;
	buffer[11] = 0x80;
	buffer[12] = 0x00;
	buffer[13] = 0x00;
	buffer[14] = 0x75;
	buffer[15] = 0x30;*/

}

void FormateData(Point3i pt, Speed speed, char* buffer, int flag)
{
	buffer[0] = flag & 255;
	//X
	buffer[1] = pt.x >> 24;
	buffer[2] = (pt.x >> 16) & 255;
	buffer[3] = (pt.x >> 8) & 255;
	buffer[4] = pt.x & 255;
	//Y
	buffer[5] = pt.y >> 24;
	buffer[6] = (pt.y >> 16) & 255;
	buffer[7] = (pt.y >> 8) & 255;
	buffer[8] = pt.y & 255;
	//Z
	buffer[9] = pt.z >> 24;
	buffer[10] = (pt.z >> 16) & 255;
	buffer[11] = (pt.z >> 8) & 255;
	buffer[12] = pt.z & 255;

	//xSPEED
	buffer[13] = speed.xSpeed >> 24;
	buffer[14] = (speed.xSpeed >> 16) & 255;
	buffer[15] = (speed.xSpeed >> 8) & 255;
	buffer[16] = speed.xSpeed & 255;

	//ySPEED
	buffer[17] = speed.ySpeed >> 24;
	buffer[18] = (speed.ySpeed >> 16) & 255;
	buffer[19] = (speed.ySpeed >> 8) & 255;
	buffer[20] = speed.ySpeed & 255;

	//zSPEED
	buffer[21] = speed.zSpeed >> 24;
	buffer[22] = (speed.zSpeed >> 16) & 255;
	buffer[23] = (speed.zSpeed >> 8) & 255;
	buffer[24] = speed.zSpeed & 255;
	buffer[25] = '\n';
	/*char temp;
	//X
	buffer[1] = pt.x >> 25;
	buffer[1] = (buffer[1] << 1) | 1;
	buffer[2] = (pt.x >> 18) & 127;
	buffer[2] = (buffer[2] << 1) | 1;
	buffer[3] = (pt.x >> 11) & 127;
	buffer[3] = (buffer[3] << 1) | 1;
	buffer[4] = (pt.x >> 4) & 127;
	buffer[4] = (buffer[4] << 1) | 1;
	buffer[5] = pt.x & 15;
	buffer[5] = (buffer[5] << 3);
	temp = (pt.y >> 29) & 7;
	buffer[5] = buffer[5] | temp;
	buffer[5] = (buffer[5] << 1) | 1;
	//Y
	buffer[6] = (pt.y >> 22) & 127;
	buffer[6] = (buffer[6] << 1) | 1;
	buffer[7] = (pt.y >> 15) & 127;
	buffer[7] = (buffer[7] << 1) | 1;
	buffer[8] = (buffer[8] >> 8) & 127;
	buffer[8] = (buffer[8] << 1) | 1;
	buffer[9] = (buffer[9] >> 1) & 127;
	buffer[9] = (buffer[9] << 1) | 1;
	buffer[10] = pt.y & 1;
	buffer[10] = buffer[10] << 6;
	temp = (pt.z >> 26) & 63;
	buffer[10] = buffer[10] | temp;
	buffer[10] = (buffer[10] << 1) | 1;
	//Z
	buffer[11] = (pt.z >> 19) & 127;
	buffer[11] = (buffer[11] << 1) | 1;
	buffer[12] = (pt.z >> 12) & 127;
	buffer[12] = (buffer[12] << 1) | 1;
	buffer[13] = (pt.z >> 5) & 127;
	buffer[13] = (buffer[13] << 1) | 1;
	buffer[14] = pt.z & 31;
	buffer[14] = buffer[14] << 2;
	temp = (speed.xSpeed >> 30) & 3;
	buffer[14] = buffer[14] | temp;
	buffer[14] = (buffer[14] << 1) | 1;
	//xSPEED
	buffer[15] = (speed.xSpeed >> 23) & 127;
	buffer[15] = (buffer[15] << 1) | 1;
	buffer[16] = (speed.xSpeed >> 16) & 127;
	buffer[16] = (buffer[16] << 1) | 1;
	buffer[17] = (speed.xSpeed >> 9) & 127;
	buffer[17] = (buffer[17] << 1) | 1;
	buffer[18] = (speed.xSpeed >> 2) & 127;
	buffer[18] = (buffer[18] << 1) | 1;
	buffer[19] = speed.xSpeed & 3;
	buffer[19] = buffer[19] << 5;
	temp = (speed.ySpeed >> 27) & 31;
	buffer[19] = buffer[19] | temp;
	buffer[19] = (buffer[19] << 1) | 1;

	//ySPEED
	buffer[20] = (speed.ySpeed >> 20) & 127;
	buffer[20] = (buffer[20] << 1) | 1;
	buffer[21] = (speed.ySpeed >> 13) & 127;
	buffer[21] = (buffer[21] << 1) | 1;
	buffer[22] = (speed.ySpeed >> 6) & 127;
	buffer[22] = (buffer[22] << 1) | 1;
	buffer[23] = speed.ySpeed & 63;
	buffer[23] = buffer[23] << 1;
	temp = (speed.zSpeed >> 31) & 1;
	buffer[23] = buffer[23] | temp;
	buffer[23] = (buffer[23] << 1) | 1;

	//zSPEED
	buffer[24] = (speed.zSpeed >> 24) & 127;
	buffer[24] = (buffer[24] << 1) | 1;
	buffer[25] = (speed.zSpeed >> 17) & 127;
	buffer[25] = (buffer[25] << 1) | 1;
	buffer[26] = (speed.zSpeed >> 10) & 127;
	buffer[26] = (buffer[26] << 1) | 1;
	buffer[27] = (speed.zSpeed >> 3) & 127;
	buffer[27] = (buffer[27] << 1) | 1;
	buffer[28] = speed.zSpeed & 7;
	buffer[28] = buffer[28] << 4;
	temp = 0;
	buffer[28] = buffer[19] | temp;
	buffer[28] = (buffer[19] << 1) | 1;
	buffer[29] = '\n';*/
}

double MoveTime(Point3i start, Point3i end, Speed sp, double delay)
{
	double xTime = abs(start.x - end.x) / sp.xSpeed + delay;
	double yTime = abs(start.y - end.y) / sp.ySpeed + delay;
	double zTime = abs(start.z - end.z) / sp.zSpeed + delay;

	if (xTime > yTime)
		return xTime > zTime ? xTime : zTime;
	else
		return yTime > zTime ? yTime : zTime;
}