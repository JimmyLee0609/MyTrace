package myLog;

import java.util.Map;
import java.util.Map.Entry;

import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.FileAppender;

public class Mylog<E> extends FileAppender<E>{
	private long i=0l;
	protected boolean checkForFileCollisionInPreviousFileAppenders() {
        boolean collisionsDetected = false;
        if (fileName == null) {
            return false;
        }
        @SuppressWarnings("unchecked")
        
        Map<String, String> map = (Map<String, String>) context.getObject(CoreConstants.FA_FILENAME_COLLISION_MAP);
        if (map == null) {
            return collisionsDetected;
        }
        
        for (Entry<String, String> entry : map.entrySet()) {
            if (fileName.equals(entry.getValue())) {
                addErrorForCollision("File", entry.getValue(), entry.getKey());
                collisionsDetected = true;
            }
        }
        if (name != null) {
            map.put(getName(), fileName);
        }
        return collisionsDetected;
    }
}
