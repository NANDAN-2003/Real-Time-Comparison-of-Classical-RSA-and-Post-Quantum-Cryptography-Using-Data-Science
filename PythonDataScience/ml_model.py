import pandas as pd
from sklearn.tree import DecisionTreeClassifier

# Load data
data = pd.read_csv(
    "results.csv",
    header=None,
    names=["Algorithm", "KeySize", "EncryptionTime", "AttackResult"]
)

# Encode categorical values
data["AlgorithmCode"] = data["Algorithm"].apply(
    lambda x: 0 if x == "RSA" else 1
)

data["AttackLabel"] = data["AttackResult"].apply(
    lambda x: 0 if "BROKEN" in x else 1
)

# Features and label
X = data[["AlgorithmCode", "KeySize", "EncryptionTime"]]
y = data["AttackLabel"]

# Train ML model
model = DecisionTreeClassifier()
model.fit(X, y)

# Predict results
predictions = model.predict(X)

data["ML_Prediction"] = predictions

print("\nML Classification Results:")
for index, row in data.iterrows():
    status = "Quantum-Safe" if row["ML_Prediction"] == 1 else "Quantum-Vulnerable"
    print(f"{row['Algorithm']} → {status}")
