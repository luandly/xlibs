package com.weshop.util.xlibsutils;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


import android.os.Environment;
import android.util.Log;

/**
 * 文件工具类
 */
public class AndlyFileUtil {
    private static final String TAG = "AndlyFileUtil";

    /**
     * 拷贝文件
     *
     * @param fromFile
     * @param toFile
     * @throws IOException
     */
    public static void copyFile(File fromFile, String toFile)
            throws IOException {

        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = from.read(buffer)) != -1)
                to.write(buffer, 0, bytesRead); // write
        } finally {
            if (from != null)
                try {
                    from.close();
                } catch (IOException e) {
                    Log.e(TAG, "", e);
                }
            if (to != null)
                try {
                    to.close();
                } catch (IOException e) {
                    Log.e(TAG, "", e);
                }
        }
    }
    
    /**
     * 复制文件夹及其中的文件
     *
     * @param oldPath String 原文件夹路径 如：data/user/0/com.test/files
     * @param newPath String 复制后的路径 如：data/user/0/com.test/cache
     * @return <code>true</code> if and only if the directory and files were copied;
     * <code>false</code> otherwise
     */
    public boolean copyFolder(String oldPath, String newPath) {
        try {
            File newFile = new File(newPath);
            if (!newFile.exists()) {
                if (!newFile.mkdirs()) {
                    Log.d("--Method--", "copyFolder: cannot create directory.");
                    return false;
                }
            }
            File oldFile = new File(oldPath);
            String[] files = oldFile.list();
            File temp;
            for (String file : files) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file);
                } else {
                    temp = new File(oldPath + File.separator + file);
                }

                if (temp.isDirectory()) {   //如果是子文件夹
                    copyFolder(oldPath + "/" + file, newPath + "/" + file);
                } else if (!temp.exists()) {
                    Log.d("--Method--", "copyFolder:  oldFile not exist.");
                    return false;
                } else if (!temp.isFile()) {
                    Log.d("--Method--", "copyFolder:  oldFile not file.");
                    return false;
                } else if (!temp.canRead()) {
                    Log.d("--Method--", "copyFolder:  oldFile cannot read.");
                    return false;
                } else {
                    FileInputStream fileInputStream = new FileInputStream(temp);
                    FileOutputStream fileOutputStream = new FileOutputStream(newPath + "/" + temp.getName());
                    byte[] buffer = new byte[1024];
                    int byteRead;
                    //!=-1 也可以写成！=null,意思是读取的数据不为负数或者null就说明还没有读取完毕
                    while ((byteRead = fileInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, byteRead);
                    }
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建文件
     *
     * @param file
     * @return
     */
    public static File createNewFile(File file) {

        try {

            if (file.exists()) {
                return file;
            }

            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            Log.e(TAG, "", e);
            return null;
        }
        return file;
    }

    /**
     * 创建文件
     *
     * @param path
     */
    public static File createNewFile(String path) {
        File file = new File(path);
        return createNewFile(file);
    }// end method createText()

    /**
     * 删除文件
     *
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        deleteFile(file);
    }

    /**
     * 删除文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i]);
            }
        }
        file.delete();
    }

    /**
     * 向Text文件中写入内容
     *
     * @param file
     * @param content
     * @return
     */
    public static boolean write(String path, String content) {
        return write(path, content, false);
    }

    public static boolean write(String path, String content, boolean append) {
        return write(new File(path), content, append);
    }

    public static boolean write(File file, String content) {
        return write(file, content, false);
    }

    public static boolean write(File file, String content, boolean append) {
        if (file == null || AndlyStringUtils.isNullOrEmpty(content)) {
            return false;
        }
        if (!file.exists()) {
            file = createNewFile(file);
        }
        FileOutputStream ops = null;
        try {
            ops = new FileOutputStream(file, append);
            ops.write(content.getBytes());
        } catch (Exception e) {
            Log.e(TAG, "", e);
            return false;
        } finally {
            try {
                ops.close();
            } catch (IOException e) {
                Log.e(TAG, "", e);
            }
            ops = null;
        }

        return true;
    }

    public static File getCacheFile(String url) {
        File file1 = Environment.getExternalStorageDirectory();
        File file = new File(file1, getFileNameUrl(url));
        return file;
    }

    /**
     * 获得文件名
     *
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        if (AndlyStringUtils.isNullOrEmpty2(path)) {
            return null;
        }
        File f = new File(path);
        String name = f.getName();
        f = null;
        return name;
    }

    public static String getFileNameUrl(String url) {
        String filename = "";
        boolean isok = false;
        // 从UrlConnection中获取文件名称
        try {
            URL myURL = new URL(url);


            URLConnection conn = myURL.openConnection();
            if (conn == null) {
                return null;
            }
            Map<String, List<String>> hf = conn.getHeaderFields();
            if (hf == null) {
                return null;
            }
            Set<String> key = hf.keySet();
            if (key == null) {
                return null;
            }


            for (String skey : key) {
                List<String> values = hf.get(skey);
                for (String value : values) {
                    String result;
                    try {
                        result = new String(value.getBytes("ISO-8859-1"), "GBK");
                        int location = result.indexOf("filename");
                        if (location >= 0) {
                            result = result.substring(location
                                    + "filename".length());
                            filename = result
                                    .substring(result.indexOf("=") + 1);
                            isok = true;
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }// ISO-8859-1 UTF-8 gb2312
                }
                if (isok) {
                    break;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 从路径中获取
        if (filename == null || "".equals(filename)) {
            filename = url.substring(url.lastIndexOf("/") + 1);
        }
        return filename;
    }

    /**
     * 读取文件内容，从第startLine行开始，读取lineCount行
     *
     * @param file
     * @param startLine
     * @param lineCount
     * @return 读到文字的list, 如果list.size<lineCount则说明读到文件末尾了
     */
    public static List<String> readFile(File file, int startLine, int lineCount) {
        if (file == null || startLine < 1 || lineCount < 1) {
            return null;
        }
        if (!file.exists()) {
            return null;
        }
        FileReader fileReader = null;
        List<String> list = null;
        try {
            list = new ArrayList<String>();
            fileReader = new FileReader(file);
            LineNumberReader lnr = new LineNumberReader(fileReader);
            boolean end = false;
            for (int i = 1; i < startLine; i++) {
                if (lnr.readLine() == null) {
                    end = true;
                    break;
                }
            }
            if (end == false) {
                for (int i = startLine; i < startLine + lineCount; i++) {
                    String line = lnr.readLine();
                    if (line == null) {
                        break;
                    }
                    list.add(line);

                }
            }
            if (lnr != null) {
                lnr.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "read log error!", e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return list;
    }

    /**
     * 创建文件夹
     *
     * @param dir
     * @return
     */
    public static boolean createDir(File dir) {
        try {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "create dir error", e);
            return false;
        }
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     */
    public static File creatSDDir(String dirName) {
        File dir = new File(dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 判断SD卡上的文件是否存在
     */
    public static boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     */
    public static File write2SDFromInput(String path, String fileName,
                                         InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            creatSDDir(path);
            file = createNewFile(path + "/" + fileName);
            output = new FileOutputStream(file);
            byte buffer[] = new byte[1024];
            int len = -1;
            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    // 从文件中一行一行的读取文件
    public static String readFile(File file) {
        Reader read = null;
        String content = "";
        String string = "";
        BufferedReader br = null;
        try {
            read = new FileReader(file);
            br = new BufferedReader(read);
            while ((content = br.readLine().toString().trim()) != null) {
                string += content + "\r\n";
            }
            // BufferedReader reader = new BufferedReader(new
            // InputStreamReader(inputStream));
            // String line = reader.readLine();
            // while (line != null) { ... }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                read.close();
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("string=" + string);
        return string.toString();
    }

    /**
     * 把字节数组保存为一个文件
     *
     * @param b
     * @param outputFile
     * @return
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        File ret = null;
        BufferedOutputStream stream = null;
        try {
            ret = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(ret);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            // log.error("helper:get file from byte process error!");
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // log.error("helper:get file from byte process error!");
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    /**
     * 文件转化为字节数组
     *
     * @param file
     * @return
     */
    public static byte[] getBytesFromFile(File file) {
        byte[] ret = null;
        try {
            if (file == null) {
                // log.error("helper:the file is null!");
                return null;
            }
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            byte[] b = new byte[4096];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            in.close();
            out.close();
            ret = out.toByteArray();
        } catch (IOException e) {
            // log.error("helper:get bytes from file process error!");
            e.printStackTrace();
        }
        return ret;
    }
}

