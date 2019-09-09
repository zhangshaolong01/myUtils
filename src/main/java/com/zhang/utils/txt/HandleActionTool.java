package com.zhang.utils.txt;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandleActionTool {

    public static final String FILEPATH = "D:\\workspaceIdea\\woohTemp\\wooh-basic\\src\\main\\java\\com\\wooh\\basicsys\\action";

    public static void main(String[] args) {
        System.out.println("---执行开始");
        List<File> actionFileList = new ArrayList<>();
        initActionFileList(actionFileList, FILEPATH);
        System.out.println("---获取所有Action个数：" + actionFileList.size());
        System.out.println("---开始处理Controller转换");
        for (int i = 0; i < actionFileList.size(); i++) {
            if (i != 1) {
                continue;            }
            createController(actionFileList.get(i));
        }

        Map<String, String> urlMap = new HashMap<>();
        //getFileList(fileList, path, "struts.xml");
//        for (File file : fileList) {
//            XmlUtils.handleXML(file, urlMap);
//        }
//        System.out.println(urlMap.toString());

        System.out.println("---执行结束");
    }

    /**
     * 获取所有Action
     *
     * @param fileList
     * @param path
     */
    public static void initActionFileList(List<File> fileList, String path) {
        File dir = new File(path);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
            if (files != null && files.length > 0) {
                for (File file : files) {
                    String fileName = file.getName();
                    if (file.isDirectory()) { // 判断是文件还是文件夹
                        initActionFileList(fileList, file.getAbsolutePath()); // 获取文件绝对路径
                    } else if (fileName.endsWith("Action.java")) { // 判断文件名是否以reg结尾
                        fileList.add(file);
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    /**
     * 修改行
     *
     * @param actionFile
     */
    public static void createController(File actionFile) {
        System.out.println("==============================================");
        System.out.println("处理文件:" + actionFile.getPath());
        BufferedReader reader = null;
        BufferedWriter bw = null;
        try {
            //打开文件
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(actionFile), "UTF-8"));
            //内存流
            List<String> lineList = new ArrayList<>();
            String className = "";
            String listName = "";//返回数据名称
            int index = 0;
            int funtionIndex = 0;//函数行数
            boolean pageFlag = false;//分页判断
            boolean catchFlag = false;//异常判断
            boolean returnFlag = false;//是否有返回判断
            //按行读取文件
            String line = null;
            while ((line = reader.readLine()) != null) {
                String trimStr = line.trim();
                if (trimStr.length() == 0) {
                    continue;
                }
                //System.out.println("line: " + line);
                if (trimStr.startsWith("package")) {
                    line = line.replace("action", "controller");
                    System.out.println("文件包名:" + line);
                    lineList.add(line + System.getProperty("line.separator"));
                    lineList.add(System.getProperty("line.separator"));
                } else if (trimStr.startsWith("import")) {
                    if (trimStr.contains("PageAction")) {
                        line = line.replaceAll("Action", "Controller");
                        System.out.println("更换继承类:" + line);
                    }
                    lineList.add(line + System.getProperty("line.separator"));
                } else if (trimStr.contains("PageAction")) {
                    lineList.add(System.getProperty("line.separator"));
                    lineList.add("@Controller" + System.getProperty("line.separator"));
                    lineList.add("@RequestMapping(value=\"/omp\")" + System.getProperty("line.separator"));
                    line = line.replaceAll("Action", "Controller");
                    lineList.add(line + System.getProperty("line.separator"));

                    String[] trimStrs = trimStr.split("extends")[0].trim().split(" ");
                    String actionName = trimStrs[trimStrs.length - 1];
                    actionName = actionName.substring(0, 1).toLowerCase() + actionName.substring(1);
                    className = actionName.replaceAll("Action", "");
                    System.out.println("bean名称:" + actionName + "\t实体类名称:" + className);
                } else if (trimStr.startsWith("private") && line.endsWith("Service;")) {
                    lineList.add("\t@Autowired" + System.getProperty("line.separator"));
                    lineList.add(line + System.getProperty("line.separator"));
                    lineList.add(System.getProperty("line.separator"));
                } else if (trimStr.startsWith("private List")) {
                    String[] trimStrs = trimStr.split("=");
                    String[] trimStrs2 = trimStrs[0].trim().split(" ");
                    listName = trimStrs2[trimStrs2.length - 1];
                    System.out.println("返回数据名称:" + listName);
                } else if (trimStr.startsWith("public String") && trimStr.contains("(") && !trimStr.contains(" get")) {
                    String menthName = trimStr.split("\\(\\)")[0].replace("public String", "").trim();
                    System.out.println("处理函数:" + menthName);
                    lineList.add("\t@ResponseBody" + System.getProperty("line.separator"));
                    lineList.add("\t@PostMapping(\"/" + menthName + "_" + className + "\")" + System.getProperty("line.separator"));
                    line = line.replace("()", "(String data)");
                    funtionIndex = lineList.size();
                    lineList.add(line + System.getProperty("line.separator"));
                    if (trimStr.contains("{")) {
                        index++;
                    }
                } else if (index > 0) {
                    if (line.contains("setClass")) {
                        line = line.replace("setClass(", "setClass(data,");
                    }
                    if (trimStr.contains("this.getPagesize()")) {
                        line = line.replace("this.getPagesize()", "pagesize");
                    }
                    if (trimStr.contains("this.getPagerow()")) {
                        line = line.replace("this.getPagerow()", "pagerow");
                    }
                    if (trimStr.contains("this.getSort()")) {
                        line = line.replace("this.getSort()", "sort");
                    }
                    if (trimStr.contains("this.getSort()")) {
                        line = line.replace("this.getDir()", "dir");
                    }
                    if (trimStr.startsWith("Page")) {
                        pageFlag = true;
                        String linePage = lineList.get(funtionIndex);
                        linePage = linePage.replace("(String data)", "(String data,int pagesize,int pagerow,String sort,String dir)");
                        lineList.set(funtionIndex, linePage);
                        System.out.println("判断该函数为分页查询:" + linePage);
                    } else if (trimStr.contains("page.getData()")) {
                        line = "\t\t\tList " + trimStr;
                    } else if (line.contains("page.getTotalRows()")) {
                        //line = "\t\t\tint " + trimStr;
                        continue;
                    } else if (line.contains("setTotalpage")) {
                        continue;
                    }
                    if (trimStr.contains("catch")) {
                        catchFlag = true;
                    }
                    if (trimStr.contains(listName)) {
                        returnFlag = true;
                    }
                    if (trimStr.contains("this.result")) {
                        if (pageFlag) {
                            line = "\t\t\treturn success(" + listName + ",page.getTotalRows(),\"" + listName + "\");";
                            pageFlag = false;
                        } else if (catchFlag) {
                            line = "\t\t\treturn error(e.getMessage());";
                            catchFlag = false;
                        } else if (returnFlag) {
                            line = "\t\t\treturn success(" + listName + ",\"" + listName + "\");";
                            returnFlag = false;
                        } else {
                            line = "\t\t\treturn success();";
                        }
                    } else if (trimStr.startsWith("return")) {
                        continue;
                    }

                    lineList.add(line + System.getProperty("line.separator"));
                    if (trimStr.contains("{")) {
                        index++;
                    }
                    if (trimStr.contains("}")) {
                        index--;
                        if (index == 0) {
                            lineList.add(System.getProperty("line.separator"));
                        }
                    }
                } else if (trimStr.startsWith("//") || trimStr.startsWith("/*") || trimStr.startsWith("*")) {
                    if (!trimStr.contains("TODO")) {
                        lineList.add(line + System.getProperty("line.separator"));
                    }
                } else {

                }
            }

            //生成Controller
            String controllerFilePath = actionFile.getAbsolutePath().replace("action", "controller").replace("Action", "Controller");
            System.out.println("生成Controller文件路径:" + controllerFilePath);
            File controllerFile = new File(controllerFilePath);
            if (controllerFile.exists()) {
                controllerFile.delete();
            }
            if (!controllerFile.getParentFile().exists()) {
                controllerFile.getParentFile().mkdirs();
            }
            //controllerFile.createNewFile();
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(controllerFile), "UTF-8"));
            for (String lineStr : lineList) {
                bw.write(lineStr);
            }
            System.out.println("处理文件结束");
            System.out.println("==============================================");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}