package org.cq.service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.cq.model.Attach;
import org.cq.util.DateUtil;
import org.cq.util.StringUtil;

import com.jfinal.upload.UploadFile;

public class FileService {
	
	/**
	 * 附件上传<br>
	 * 直接返回字符串，在前台去转json，不然IE会变成下载
	 */
	public Map<String,Object> file(UploadFile file,Long UserId) {
		Attach db = new Attach();
		boolean b = db
				.set("date_f", new Date())
				.set("filename_f", file.getFileName())
				.set("suffix_f",
						file.getFileName().substring(
								file.getFileName().lastIndexOf(".") + 1))
				.set("user_f", UserId)
				.set("size_f", file.getFile().length()).save();

		if (b) {
			Long attachId = db.getLong("id_f");
			file.getFile().renameTo(
					new File(file.getSaveDirectory() + "/" + attachId
							+ ".att"));
			file.getFile().deleteOnExit();
			Map<String, Object> map = new HashMap<>();
			map.put("id", db.getLong("id_f"));
			map.put("name", db.getStr("filename_f"));
			map.put("size", StringUtil.fomatFileSize(db.getLong("size_f")));
			map.put("date", DateUtil.format(db.getDate("date_f"),
					DateUtil.DATETIME_PATTERN));
			return map;
		} else {
			file.getFile().delete();
			return null;
		}
	}

}
