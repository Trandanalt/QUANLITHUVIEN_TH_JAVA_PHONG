import java.util.ArrayList;
import java.util.Scanner;

class Sach {
    protected String maSach;
    protected String ngayNhap;
    protected double donGia;
    protected int soLuong;
    protected String nhaXuatBan;

    public void nhap(Scanner sc) {
        System.out.print("Nhập mã sách: ");
        maSach = sc.nextLine();
        System.out.print("Nhập ngày nhập (dd/mm/yyyy): ");
        ngayNhap = sc.nextLine();
        System.out.print("Nhập đơn giá: ");
        donGia = Double.parseDouble(sc.nextLine());
        System.out.print("Nhập số lượng: ");
        soLuong = Integer.parseInt(sc.nextLine());
        System.out.print("Nhập nhà xuất bản: ");
        nhaXuatBan = sc.nextLine();
    }

    public void xuat() {
        System.out.print("Mã: " + maSach + " | Ngày: " + ngayNhap + 
                         " | Đơn giá: " + donGia + " | SL: " + soLuong + 
                         " | NXB: " + nhaXuatBan);
    }

    public double tinhThanhTien() {
        return 0;
    }
}

class SachGiaoKhoa extends Sach {
    private String tinhTrang;

    @Override
    public void nhap(Scanner sc) {
        super.nhap(sc);
        System.out.print("Nhập tình trạng (moi / cu): ");
        tinhTrang = sc.nextLine();
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println(" | Tình trạng: " + tinhTrang + " | Thành tiền: " + tinhThanhTien());
    }

    @Override
    public double tinhThanhTien() {
        if (tinhTrang.equalsIgnoreCase("moi")) {
            return soLuong * donGia;
        } else {
            return soLuong * donGia * 0.5;
        }
    }
}

class SachThamKhao extends Sach {
    private double thue;

    @Override
    public void nhap(Scanner sc) {
        super.nhap(sc);
        System.out.print("Nhập tiền thuế: ");
        thue = Double.parseDouble(sc.nextLine());
    }

    @Override
    public void xuat() {
        super.xuat();
        System.out.println(" | Thuế: " + thue + " | Thành tiền: " + tinhThanhTien());
    }

    @Override
    public double tinhThanhTien() {
        return soLuong * donGia + thue;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Sach> danhSach = new ArrayList<>();

        System.out.print("Bạn muốn nhập bao nhiêu cuốn sách? ");
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Nhập sách thứ " + (i + 1) + " ---");
            System.out.print("Chọn loại sách (1 - Sách giáo khoa, 2 - Sách tham khảo): ");
            int loai = Integer.parseInt(sc.nextLine());

            Sach s;
            if (loai == 1) {
                s = new SachGiaoKhoa();
            } else {
                s = new SachThamKhao();
            }
            s.nhap(sc);
            danhSach.add(s);
        }

        System.out.println("\n========== DANH SÁCH CÁC LOẠI SÁCH ==========");
        for (Sach s : danhSach) {
            s.xuat();
        }

        double tongTienSGK = 0;
        double tongTienSTK = 0;
        double tongDonGiaSTK = 0;
        int demSTK = 0;

        for (Sach s : danhSach) {
            if (s instanceof SachGiaoKhoa) {
                tongTienSGK += s.tinhThanhTien();
            } else if (s instanceof SachThamKhao) {
                tongTienSTK += s.tinhThanhTien();
                tongDonGiaSTK += s.donGia;
                demSTK++;
            }
        }

        System.out.println("\n========== THỐNG KÊ ==========");
        System.out.println("Tổng thành tiền Sách giáo khoa: " + tongTienSGK);
        System.out.println("Tổng thành tiền Sách tham khảo: " + tongTienSTK);

        if (demSTK > 0) {
            System.out.println("Trung bình cộng đơn giá Sách tham khảo: " + (tongDonGiaSTK / demSTK));
        } else {
            System.out.println("Không có sách tham khảo nào để tính trung bình cộng.");
        }

        System.out.print("\nNhập tên Nhà xuất bản cần tìm (X): ");
        String nxbCanTim = sc.nextLine();

        System.out.println("Các sách giáo khoa của NXB " + nxbCanTim + ":");
        boolean timThay = false;
        for (Sach s : danhSach) {
            if (s instanceof SachGiaoKhoa && s.nhaXuatBan.equalsIgnoreCase(nxbCanTim)) {
                s.xuat();
                timThay = true;
            }
        }
        if (!timThay) {
            System.out.println("Không tìm thấy sách giáo khoa nào của NXB này.");
        }

        sc.close();
    }
}