package com.example.helloworldgrpc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ReadQRCode {
    ImageView img_qr;
    Button btnScan;
    Context mContext;
    public ReadQRCode(Context context) {
        this.mContext = context;
    }

    public void init(){
        IntentIntegrator intentIntegrator = new IntentIntegrator((Activity) mContext);
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
       // createQR();
    }

    public void methodCall(Intent intent, int requestCode){
        if (requestCode ==111) {
            if(intent == null || intent.getData()==null) {
                Log.e("revathi", "The uri is null, probably the user cancelled the image selection process using the back button.");
                return;
            }
            Uri uri = intent.getData();
            try
            {
                Log.d("revathi","uri "+uri);
                InputStream inputStream = mContext.getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap == null)
                {
                    Log.e("revathi", "uri is not a bitmap," + uri.toString());
                    return;
                }
                int width = bitmap.getWidth(), height = bitmap.getHeight();
                int[] pixels = new int[width * height];
                bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                bitmap.recycle();
                bitmap = null;
                RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                BinaryBitmap bBitmap = new BinaryBitmap(new HybridBinarizer(source));
                MultiFormatReader reader = new MultiFormatReader();
                Result result = reader.decode(bBitmap);
                Toast.makeText(mContext, "The content of the QR image is: " + result.getText(), Toast.LENGTH_SHORT).show();
            }
            catch (FileNotFoundException e) {
                Log.e("revathi", "can not open file" + uri.toString(), e);
            } catch (NotFoundException e) {
                Log.e("revathi", "decode exception", e);
            }
        }
    }

    //user-defined method that reads the QR code
        /*public static String readQRcode(String path, String charset, Map map) throws FileNotFoundException, IOException, NotFoundException
        {
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(path)))));
            Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
            return qrCodeResult.getText();
        }*/

    /* media files should have any screenshot/images in the device else throws
    * can not open filecontent://media/external/images/media  java.io.FileNotFoundException: open failed: ENOENT (No such file or directory) */
    public void readQRFromImage() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        methodCall(pickIntent, 111);
    }

    public Bitmap createQR() {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode("bitcoin:"+2+"?amount="+100, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bmp.setPixels(pixels, 0, width, 0, 0, width, height);
            return bmp;
           // img_qr.setImageBitmap(bmp);
        } catch (WriterException e) {

        }
    /*    String qrCodeData = "Hello World!";
        String filePath = "QRCode.png";
        String charset = "UTF-8"; // or "ISO-8859-1"
        Map hintMap = new HashMap();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        BitMatrix matrix = null;
        try {
            matrix = new MultiFormatWriter().encode(
                    new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 200, 200, hintMap);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        QRCodeWriter.writeToFile(matrix, filePath.substring(filePath
                .lastIndexOf('.') + 1), new File(filePath));*/
        return null;
    }
}
