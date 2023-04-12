from flask import Flask
import torch
import torch.nn as nn
import torch.nn.functional as F
from torchvision import transforms, models
from PIL import Image
import requests as req
from io import BytesIO

url = req.get("https://puppyhome-1317060763.cos.ap-guangzhou.myqcloud.com/userinfo/defaultAvatar.png")

# 获取图片信息
response = req.get(url.url)
img = Image.open(BytesIO(response.content))
img.show()
