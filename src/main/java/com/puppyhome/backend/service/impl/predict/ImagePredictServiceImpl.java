package com.puppyhome.backend.service.impl.predict;

import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.transform.CenterCrop;
//import ai.djl.modality.cv.transform.Normalize;
//import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.translator.ImageClassificationTranslator;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import com.puppyhome.backend.service.predict.ImagePredictService;
import com.puppyhome.backend.utils.ResponseResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImagePredictServiceImpl implements ImagePredictService {

	// TODO 预测接口方法实现
	@Override
	public ResponseResult imagePredict(String url) throws IOException, ModelNotFoundException, MalformedModelException, TranslateException {

		// 通过url获取图片
		Image image = ImageFactory.getInstance().fromUrl(url);

		// 添加预处理图片类
		Translator<Image, Classifications> translator = ImageClassificationTranslator.builder()
//				.addTransform(new Resize(256))
				.addTransform(new CenterCrop(224, 224))
				.addTransform(new ToTensor())
//				.addTransform(new Normalize(
//						new float[]{0.485f, 0.456f, 0.406f},
//						new float[]{0.229f, 0.224f, 0.225f}))
//				.optApplySoftmax(true)
				.build();

		// 调用模型以进行预测
		Criteria<Image, Classifications> criteria = Criteria.builder()
				.setTypes(Image.class, Classifications.class)
				.optModelPath(Paths.get("src/main/resources/model/resnet18-puppydog.pth"))
				.optOption("mapLocation", "true") // this model requires mapLocation for GPU
				.optTranslator(translator)
				.optProgress(new ProgressBar()).build();

		ZooModel<Image, Classifications> model = criteria.loadModel();

		// 获取品种及其准确率
		Predictor<Image, Classifications> predictor = model.newPredictor();
		Classifications classifications = predictor.predict(image);

		Map<String, Object> map = new HashMap<>();

		return new ResponseResult(200, "获取成功", map);
	}
}
