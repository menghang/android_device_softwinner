/**
 * you can find the path of sdcard,flash and usbhost in here
 * @author chenjd
 * @email chenjd@allwinnertech.com
 * @data 2011-8-10
 */
package com.softwinner.explore;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
/**
 * define the root path of flash,sdcard,usbhost
 * @author chenjd
 *
 */
public class DevicePath{
	private ArrayList<String> totalDevicesList;
	private String FLASH_ROOT_PATH;
	private String SDCARD_ROOT_PATH = Environment.getExternalStorageDirectory().getPath();
	private String USBHOST_ROOT_PATH;
	private static final String TAG = DevicePath.class.getSimpleName();
	public DevicePath(Context context)
	{
		totalDevicesList = new ArrayList<String>();
		StorageManager stmg = (StorageManager) context.getSystemService(context.STORAGE_SERVICE);
		String[] list = stmg.getVolumePaths();
		for(int i = 0; i < list.length; i++)
		{
			totalDevicesList.add(list[i]);
		}
	}
	
	public String getSdStoragePath()
	{
		String path = "none";
		for(int i = 0; i < totalDevicesList.size(); i++)
		{
			if(!totalDevicesList.get(i).equals(Environment.getExternalStorageDirectory().getPath()))
			{
				if(totalDevicesList.get(i).contains("sd"))
				{
					path = totalDevicesList.get(i);
					return path;
				}
			}
		}
		return path;
	}
	
	public String getInterStoragePath()
	{
		return Environment.getExternalStorageDirectory().getPath();
	}
	
	public String getUsbStoragePath()
	{
		String path = "none";
		for(int i = 0; i < totalDevicesList.size(); i++)
		{
			if(!totalDevicesList.get(i).equals(Environment.getExternalStorageDirectory().getPath()))
			{
				if(totalDevicesList.get(i).contains("usb"))
				{
					path = totalDevicesList.get(i);
					return path;
				}
			}
		}
		return path;
	}
	
	/**
	 * ��ȡ���豸���б�
	 * @return
	 */
	public ArrayList<String> getTotalDevicesList()
	{
		return (ArrayList<String>) totalDevicesList.clone();
	}
	
	public boolean hasMultiplePartition(String dPath)
	{
		try
		{
			File file = new File(dPath);
			String minor = null;
			String major = null;
			for(int i = 0; i < totalDevicesList.size(); i++)
			{
				if(dPath.equals(totalDevicesList.get(i)))
				{
					String[] list = file.list();
					for(int j = 0; j < list.length; j++)
					{
						/* ���Ŀ¼������������"���豸��:���豸��"(��ǰ��������������),�򷵻�false */
						int lst = list[j].lastIndexOf("_");
						if(lst != -1 && lst != (list[j].length() -1))
						{
							major = list[j].substring(0, lst);
							minor = list[j].substring(lst + 1, list[j].length());
							try
							{
							
								Integer.valueOf(major);
								Integer.valueOf(minor);
							}
							catch(NumberFormatException e)
							{
								/* ������ַ������ܱ�����Ϊ����,���˳� */
								return false;
							}
						}
						else 
						{
							return false;
						}
					}
					return true;
				}
			}
			return false;
		}
		catch(Exception e)
		{
			Log.e(TAG, "hasMultiplePartition() exception e");
			return false;
		}
	}
}


