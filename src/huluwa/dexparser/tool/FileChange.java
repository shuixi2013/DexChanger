package huluwa.dexparser.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import huluwa.dexparser.type.Leb128;
import huluwa.dexparser.type.TypeCast;

public class FileChange extends ByteInserter {
	protected File file;

	public FileChange(File file) throws IOException {
		this.file = file;
		RandomAccessFile rsf = new RandomAccessFile(this.file, "rw");
		this.data = new byte[(int) rsf.length()];
		rsf.read(data);
		int pos = 0;
		while (pos < data.length) {
			instBuf.add(data[pos++]);
		}
		rsf.close();
		getLength();
	}

	public void changeInt(int val) {
		changeData(new TypeCast(val).toBytes());
	}

	public void changeByte(byte b) {
		changeData(new byte[] { b });
	}

	public void changeShort(short val) {
		changeData(new TypeCast(val).toBytes());
	}

	public void changeLeb128(Leb128 val) {
		changeData(new TypeCast(val).toBytes());
	}

	public void changeData(byte data[]) {
		int i = 0;
		while (i < data.length) {
			this.instBuf.set(this.position++, data[i++]);
		}
	}

	public void reset() {
		System.out.println("FileInserter is cannot reset");
	}

	public void flush() {
		super.flush();
		try {
			OutputStream out = new FileOutputStream(file);
			out.write(this.data);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
