import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix


# -----------------------------
# 1. Load Dataset
# -----------------------------
data = pd.read_csv("results.csv")

print("\nDataset Preview:")
print(data.head())

print("\nDataset Shape:", data.shape)


# -----------------------------
# 2. Encode Labels
# -----------------------------
data["AttackLabel"] = data["AttackResult"].apply(
    lambda x: 0 if "BROKEN" in x else 1
)

data["AlgorithmCode"] = data["Algorithm"].apply(
    lambda x: 0 if x == "RSA" else 1
)


# -----------------------------
# 3. Feature Selection
# -----------------------------
X = data[
    [
        "AlgorithmCode",
        "KeySize",
        "KeyGenerationTime(us)",
        "EncryptionTime(us)",
        "DecryptionTime(us)",
        "CiphertextSize(bytes)"
    ]
]

y = data["AttackLabel"]


# -----------------------------
# 4. Train/Test Split
# -----------------------------
X_train, X_test, y_train, y_test = train_test_split(
    X,
    y,
    test_size=0.2,
    random_state=42
)


# -----------------------------
# 5. Train ML Model
# -----------------------------
model = RandomForestClassifier(
    n_estimators=100,
    random_state=42
)

model.fit(X_train, y_train)


# -----------------------------
# 6. Predictions
# -----------------------------
y_pred = model.predict(X_test)


# -----------------------------
# 7. Model Evaluation
# -----------------------------
accuracy = accuracy_score(y_test, y_pred)

print("\nModel Accuracy:", accuracy)

print("\nClassification Report:")
print(classification_report(y_test, y_pred))


# -----------------------------
# 8. Confusion Matrix
# -----------------------------
cm = confusion_matrix(y_test, y_pred)

plt.figure(figsize=(5,4))
sns.heatmap(cm, annot=True, fmt="d", cmap="Blues")

plt.title("Confusion Matrix")
plt.xlabel("Predicted Label")
plt.ylabel("Actual Label")

plt.show()


# -----------------------------
# 9. Feature Importance
# -----------------------------
importance = model.feature_importances_

features = X.columns

importance_df = pd.DataFrame({
    "Feature": features,
    "Importance": importance
})

importance_df = importance_df.sort_values(by="Importance", ascending=False)

print("\nFeature Importance:")
print(importance_df)


plt.figure(figsize=(6,4))
sns.barplot(
    x="Importance",
    y="Feature",
    data=importance_df
)

plt.title("Feature Importance in Classification")
plt.show()


# -----------------------------
# 10. Example Predictions
# -----------------------------
print("\nExample Predictions:")

sample_predictions = model.predict(X.head(10))

for i, pred in enumerate(sample_predictions):

    result = "Quantum-Safe" if pred == 1 else "Quantum-Vulnerable"

    print(data.iloc[i]["Algorithm"], "→", result)