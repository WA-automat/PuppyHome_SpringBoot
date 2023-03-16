package com.puppyhome.backend;

import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.transform.CenterCrop;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.translator.ImageClassificationTranslator;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import com.puppyhome.backend.service.apply.ApplicationService;
import com.puppyhome.backend.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Paths;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void testJwtUtil() throws Exception {
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiNDQ2ZmU3NDU5ZjM0MmIyYjcyNjVlNjQ4ZDA4Yjk4YyIsInN1YiI6Im9KWXMxNHljXzJNSUtiRTk1cnNKOWFjRVFGaWciLCJpc3MiOiJzZyIsImlhdCI6MTY3Nzk0NTIyNiwiZXhwIjoxNjc4MjA0NDI2fQ.rtexwte43uFtvHjVBvaZ84qu91VnujJbFbSEGJaAHKY";
		System.out.println(JwtUtil.parseJWT(jwt).getSubject());
	}

	@Autowired
	private ApplicationService applicationService;

	@Test
	void testApplicationService() {
		applicationService.deleteApply(4);
	}

	@Test
	void testPredictor() throws ModelNotFoundException, MalformedModelException, IOException {

		// 添加预处理图片类
		Translator<Image, Classifications>  translator
				= ImageClassificationTranslator.builder()
//				.addTransform(new Resize(256))
				.addTransform(new CenterCrop(224, 224))
				.addTransform(new ToTensor())
//				.addTransform(new Normalize(
//						new float[]{0.485f, 0.456f, 0.406f},
//						new float[]{0.229f, 0.224f, 0.225f}))
//				.optApplySoftmax(true)
				.build();

		// 调用模型以进行预测
//		System.setProperty("ai.djl.repository.zoo.location", "build/pytorch_models/resnet18-puppyhome-v23.pth");
		Criteria<Image, Classifications> criteria = Criteria.builder()
				.optApplication(Application.CV.IMAGE_CLASSIFICATION)
				.setTypes(Image.class, Classifications.class)
				.optEngine("PyTorch")
				.optModelUrls("https://puppyhome-1317060763.cos.ap-guangzhou.myqcloud.com/build/")
				.optModelName("resnet18-puppyhome-v25.pth")
//				.optOption("mapLocation", "true") // this model requires mapLocation for GPU
				.optTranslator(translator)
//				.optProgress(new ProgressBar())
				.build();

		ZooModel<Image, Classifications> model = criteria.loadModel();

	}

}
