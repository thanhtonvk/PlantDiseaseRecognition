package com.tondz.nhandienbenhcaytrong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;

public class Common {
    public static Bitmap bitmap;
    public static String result;
    public static String[] laCams = new String[]{
            "đốm đen", "tàn nhang", "xanh hóa", "khỏe mạnh", "hắc tố"
    };
    public static String[] chuaLaCams = new String[]{
            "Đốm đen (Black Spot)\n" +
                    "Nhận biết:\n" +
                    "\n" +
                    "Xuất hiện các đốm tròn nhỏ màu đen hoặc nâu đậm trên lá.\n" +
                    "\n" +
                    "Các đốm có thể lõm nhẹ xuống và đôi khi có quầng vàng xung quanh.\n" +
                    "\n" +
                    "Nếu bệnh nặng, lá vàng và rụng sớm.\n" +
                    "\n" +
                    "Nguyên nhân: Nấm Guignardia citricarpa.\n" +
                    "\n" +
                    "Điều trị:\n" +
                    "\n" +
                    "Cắt tỉa và tiêu hủy lá, cành bệnh.\n" +
                    "\n" +
                    "Phun thuốc gốc đồng, Mancozeb hoặc Azoxystrobin.\n" +
                    "\n" +
                    "Giữ vườn thông thoáng, tránh ẩm thấp.",
            "Tàn nhang (Melanose)\n" +
                    "Nhận biết:\n" +
                    "\n" +
                    "Lá, quả và cành non xuất hiện các vết chấm nhỏ màu nâu đậm hoặc đen, hơi nhô cao.\n" +
                    "\n" +
                    "Vết bệnh phân bố rải rác, nhìn giống như tàn nhang.\n" +
                    "\n" +
                    "Nguyên nhân: Nấm Diaporthe citri.\n" +
                    "\n" +
                    "Điều trị:\n" +
                    "\n" +
                    "Cắt bỏ và tiêu hủy cành chết, cành khô.\n" +
                    "\n" +
                    "Phun thuốc gốc đồng hoặc thiophanate-methyl khi mùa mưa bắt đầu.",
            "Xanh hóa (Greening / Huanglongbing - HLB)\n" +
                    "Nhận biết:\n" +
                    "\n" +
                    "Lá biến màu lốm đốm xanh-vàng, biến dạng bất đối xứng.\n" +
                    "\n" +
                    "Cây còi cọc, ra hoa trái mùa, trái nhỏ, méo mó và ít nước.\n" +
                    "\n" +
                    "Là một trong những bệnh nguy hiểm nhất trên cây cam.\n" +
                    "\n" +
                    "Nguyên nhân: Vi khuẩn Candidatus Liberibacter spp., truyền qua rầy chổng cánh (Diaphorina citri).\n" +
                    "\n" +
                    "Điều trị:\n" +
                    "\n" +
                    "Hiện chưa có thuốc đặc trị.\n" +
                    "\n" +
                    "Kiểm soát rầy chổng cánh bằng thuốc trừ sâu sinh học hoặc hóa học.\n" +
                    "\n" +
                    "Chọn giống sạch bệnh khi trồng mới.\n" +
                    "\n" +
                    "Chăm sóc cây tốt để tăng sức đề kháng.",
            "Khỏe mạnh\n" +
                    "Nhận biết:\n" +
                    "\n" +
                    "Lá xanh tươi, bóng, không có đốm bệnh, phát triển bình thường.\n" +
                    "\n" +
                    "Ghi chú:\n" +
                    "\n" +
                    "Duy trì chăm sóc cây đúng kỹ thuật, kiểm tra định kỳ để phòng bệnh.",
            "Hắc tố (Sooty Mold)\n" +
                    "Nhận biết:\n" +
                    "\n" +
                    "Mặt trên của lá, cành, quả bị phủ một lớp mốc đen như nhọ nồi.\n" +
                    "\n" +
                    "Lớp đen này do nấm phát triển trên dịch ngọt (mật) mà sâu bệnh như rệp, rầy tiết ra.\n" +
                    "\n" +
                    "Nguyên nhân: Các loài nấm sống nhờ mật tiết ra từ rệp sáp, rầy mềm.\n" +
                    "\n" +
                    "Điều trị:\n" +
                    "\n" +
                    "Phun thuốc trừ rệp, rầy trước tiên (dùng dầu khoáng, imidacloprid).\n" +
                    "\n" +
                    "Sau đó, vệ sinh nhẹ nhàng để loại bỏ lớp mốc.\n" +
                    "\n" +
                    "Tăng cường thông thoáng cho vườn.\n" +
                    "\n"

    };
    public static String[] laDaos = {"Đốm vi kuẩn", "Khỏe mạnh"};
    public static String[] chuaLaDaos = {
            "Peach Bacterial spot (Bệnh đốm vi khuẩn trên lá đào)\n" +
                    "Nhận biết:\n" +
                    "\n" +
                    "Trên lá xuất hiện các đốm nhỏ màu tím sẫm hoặc nâu đen.\n" +
                    "\n" +
                    "Các đốm này có thể lan rộng, làm lá rách hoặc thủng lỗ.\n" +
                    "\n" +
                    "Trên quả, vết bệnh là các đốm đen, hơi lõm xuống, bề mặt nhám.\n" +
                    "\n" +
                    "Nặng có thể làm rụng lá, giảm năng suất và chất lượng quả.\n" +
                    "\n" +
                    "Nguyên nhân: Vi khuẩn Xanthomonas arboricola pv. pruni gây ra.\n" +
                    "\n" +
                    "Điều trị:\n" +
                    "\n" +
                    "Tỉa thưa vườn để tăng độ thông thoáng, giảm ẩm.\n" +
                    "\n" +
                    "Cắt bỏ và tiêu hủy lá, cành, quả bị bệnh.\n" +
                    "\n" +
                    "Phun phòng bằng thuốc gốc đồng (Copper Hydroxide) hoặc Streptomycin vào đầu mùa mưa.\n" +
                    "\n" +
                    "Chọn giống đào kháng bệnh khi trồng mới.",
            "Nhận biết:\n" +
                    "\n" +
                    "Lá xanh tươi, nguyên vẹn, không có vết đốm, không bị thủng.\n" +
                    "\n" +
                    "Quả phát triển bình thường, không có đốm đen hoặc vết lõm.\n" +
                    "\n" +
                    "Ghi chú:\n" +
                    "\n" +
                    "Tiếp tục chăm sóc cây đúng kỹ thuật (bón phân cân đối, tỉa cành hợp lý, tưới nước đúng cách).\n" +
                    "\n" +
                    "Kiểm tra thường xuyên để phát hiện sớm dấu hiệu bất thường.\n" +
                    "\n"
    };

    public static Bitmap decodeUri(Uri selectedImage, Context context) throws FileNotFoundException {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 400;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(selectedImage), null, o2);
    }

}
