import com.google.gson.Gson
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Writer
import java.util.Random
import java.awt.geom.AffineTransform


class JsontoJava {
        private String path
                private String outputPath
                private int pdfLength
                private int WID
                private int JID

                public String getPath() {return path}
        public String getOutput() {return outputPath}
        public int getpdfLength() {return pdfLength}
        public int getWID() {return WID}
        public int getJID() {return JID}

        public void setPath(String path) {this.path = path}
        public void setOutput(String outputPath) {this.outputPath = outputPath}
        public void setpdfLength(int pdfLength) {this.pdfLength = pdfLength}
        public void setWID(int WID) {this.WID = WID}
        public void setJID(int JID) {this.JID = JID}
}


