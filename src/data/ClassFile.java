package data;

import java.io.File;
import java.util.List;

import org.apache.bcel.classfile.JavaClass;

public class ClassFile {
    private File mFile;
    private JavaClass mJavaClass;
    private List<File> mInheritList;

    public void setFile(final File file){
        this.mFile = file;
    }
    public File getFile(){
        return this.mFile;
    }
    public void setInheritList(final List<File> files){
        this.mInheritList = files;
    }
    public List<File> getInheritList(){
        return this.mInheritList;
    }
    public void setJavaClass(final JavaClass jClass){
        this.mJavaClass = jClass;
    }
    public JavaClass getJavaClass(){
        return this.mJavaClass;
    }
}
