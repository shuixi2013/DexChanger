package huluwa.dexparser.type;

public class Leb128 {
	private byte data[];

	public static Leb128 Create(int value) {
		byte dest[] = new byte[5];
		int poc = 0;
		while (value != 0) {
			dest[poc++] = (byte) (value & 0x7F | 0x80);
			value >>= 7;
		}
		if (poc > 0) {
			dest[poc - 1] &= 0x7F;
		}
		byte data[] = new byte[poc];
		System.arraycopy(dest, 0, data, 0, poc);
		return new Leb128(data);
	}

	public Leb128(byte data[]) {
		this.data = GetLeb128(data);
	}

	public int toInt() {
		int i = 0;
		int val = 0;
		while (i < data.length) {
			val += (data[i] & 0x7F) << (7 * i);
			i++;
		}
		return val;
	}

	public byte[] getData() {
		return this.data;
	}

	public static byte[] GetLeb128(byte[] data) {
		int i = 0;
		while ((data[i] >> 7) != 0) {
			i++;
			if (i >= data.length) {
				return null;
			}
		}
		byte d[] = new byte[i + 1];
		System.arraycopy(data, 0, d, 0, d.length);
		return d;
	}

	public int getLength() {
		return data.length;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(toInt());
	}
}
