import numpy as np
from flask import Flask
from flask import request
import torch
import torch.nn as nn
import torch.nn.functional as F
from torchvision import transforms, models
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
    img = img.resize((224, 224))
    img = np.array(img)
    print(img.shape)

    # 图像预处理
    img_tensor = torch.from_numpy(img)

    img_tensor = img_tensor.transpose(0, 2)
    img_tensor = img_tensor.reshape((1, 3, 224, 224))
    # print(img_tensor.shape)
    # print(img_tensor.shape)

    # 加载模型并预测
    model = models.resnet18(pretrained=False)
    # model = nn.Sequential(
    #     nn.Conv2d(3, 32, kernel_size=(5, 5), padding=1),
    #     nn.ReLU(),
    #     nn.MaxPool2d(kernel_size=(2, 2), stride=(2, 2), padding=1),
    #     nn.Conv2d(32, 64, kernel_size=(2, 2), padding=1),
    #     nn.ReLU(),
    #     nn.MaxPool2d(kernel_size=(2, 2), stride=(2, 2)),
    #     nn.Flatten(),
    #     nn.Linear(200704, 1024),
    #     nn.ReLU(),
    #     nn.Linear(1024, 512),
    #     nn.ReLU(),
    #     nn.Linear(512, 256),
    #     nn.ReLU(),
    #     nn.Linear(256, 120)
    # )
    # 进行预训练模型的修改
    model.fc = nn.Linear(512, 120)
    # model.classifier.add_module("fc", nn.Linear(1000, 120))
    model.load_state_dict(torch.load('resnet18_puppyhome-v59.pt', map_location=torch.device('cpu')))

    # 测试时，一定要加上这一行！！！
    model.eval()

    output = model(img_tensor.to(torch.float32))
    # _, prediction = torch.max(output, 1)
    probability = F.softmax(output, dim=1)  # 计算softmax，即该图片属于各类的概率
    probability, prediction = torch.max(probability, 1)

    # 将预测结果从tensor转为array，并抽取结果
    # prediction = prediction.numpy()[0]

    mp = {'probability': max(probability.detach().numpy()[0] * 1, 0.01), 'type': str(prediction.numpy()[0])}

    return mp


if __name__ == '__main__':
    app.run()
