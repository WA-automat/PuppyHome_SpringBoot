import numpy as np
from flask import Flask
from flask import request
import torch
import torch.nn as nn
import torch.nn.functional as F
from torchvision import models
from PIL import Image
import requests as req
from io import BytesIO

app = Flask(__name__)


@app.route('/load/model')
def load_model():  # put application's code here

    url = request.args.get("url")

    # 获取图片信息
    response = req.get(url)
    img = Image.open(BytesIO(response.content))
    # img.show()
    img = img.resize((224, 224))
    img = np.array(img)
    # print(img.shape)

    # 图像预处理
    img_tensor = torch.from_numpy(img)

    img_tensor = img_tensor.transpose(0, 2)
    img_tensor = img_tensor.reshape((1, 3, 224, 224))
    # print(img_tensor.shape)
    # print(img_tensor.shape)

    # 加载模型并预测
    model = models.resnet50(pretrained=False)

    # 进行预训练模型的修改
    in_features_number = model.fc.in_features
    model.fc = nn.Linear(in_features_number, 120)
    model.load_state_dict(torch.load('resnet50_puppyhome-v61.pt', map_location=torch.device('cpu')))

    # 测试时，一定要加上这一行！！！
    model.eval()

    output = model(img_tensor.to(torch.float32))
    # _, prediction = torch.max(output, 1)
    probability = F.softmax(output, dim=1)  # 计算softmax，即该图片属于各类的概率
    probability, prediction = torch.max(probability, 1)

    # 将预测结果从tensor转为array，并抽取结果
    mp = {'probability': max(probability.detach().numpy()[0] * 1, 0.01), 'type': str(prediction.numpy()[0])}

    return mp


if __name__ == '__main__':
    app.run()
