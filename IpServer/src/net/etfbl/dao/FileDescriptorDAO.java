package net.etfbl.dao;

import java.util.ArrayList;

import net.etfbl.dao.beans.FileDescriptor;

public interface FileDescriptorDAO {

	public ArrayList<FileDescriptor> getAll();
	public boolean addFile(FileDescriptor fileDescriptor);
	
}
