import joblib
from fastapi import FastAPI


model=joblib.load('category_model.joblib')
vectorizer=joblib.load('vectorizer_d.joblib')

app=FastAPI()

category_map = {0:'shopping', 1:'technology', 2:'food', 3:'entertainment', 4:'transport'}

@app.post("/predict")
async def predict_expense(data: dict):
    desc = data['description']
    X = vectorizer.transform([desc])
    y_pred = model.predict(X)[0]
    return {"category": category_map[y_pred]}

