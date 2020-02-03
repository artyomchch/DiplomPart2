package com.example.diplompart2.analyze_fragments.static_analyze_2.permission;

import android.util.Log;

import java.io.InputStream;
import java.util.jar.JarFile;

import static com.example.diplompart2.analyze_fragments.static_analyze_2.permission.AndroidDecompress.decompressXML;

public class GetIntents {
    public String getIntents(String path, String XMLFile) {
        try {
            JarFile jf = new JarFile(path);
            InputStream is = jf.getInputStream(jf.getEntry("AndroidManifest.xml"));
            byte[] xml = new byte[is.available()];
            int br = is.read(xml);
            //Tree tr = TrunkFactory.newTree();
            ///// Log.v("ManifestFile", decompressXML(xml));
            ///// System.out.println(decompressXML(xml).length());
            XMLFile = decompressXML(xml);
            //prt("XML\n"+tr.list());
        } catch (Exception ex) {
            Log.v("ParseManifest","getIntents, ex: "+ex);  ex.printStackTrace();
        }
        return XMLFile;
    }
}
