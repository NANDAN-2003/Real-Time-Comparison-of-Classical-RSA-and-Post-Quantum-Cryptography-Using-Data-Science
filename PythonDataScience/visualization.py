import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Load dataset
data = pd.read_csv("results.csv")

# Convert time values from microseconds to milliseconds ONLY for plotting
data["EncryptionTime(ms)"] = data["EncryptionTime(us)"] / 1000
data["DecryptionTime(ms)"] = data["DecryptionTime(us)"] / 1000

# -----------------------------
# 1. Encryption Time Comparison
# -----------------------------
avg_encryption = data.groupby("Algorithm")["EncryptionTime(ms)"].mean()

plt.figure()
avg_encryption.plot(kind="bar", color=["blue", "green"])

plt.title("Average Encryption Time: RSA vs PQC")
plt.xlabel("Algorithm")
plt.ylabel("Encryption Time (ms)")

plt.show()

# -----------------------------
# 2. Ciphertext Size Comparison
# -----------------------------
avg_cipher = data.groupby("Algorithm")["CiphertextSize(bytes)"].mean()

plt.figure()
avg_cipher.plot(kind="bar", color=["red", "purple"])

plt.title("Ciphertext Size Comparison")
plt.xlabel("Algorithm")
plt.ylabel("Ciphertext Size (bytes)")

plt.show()

# -----------------------------
# 3. Quantum Attack Outcome
# -----------------------------
attack_counts = data["AttackResult"].value_counts()

plt.figure()
plt.pie(
    attack_counts,
    labels=attack_counts.index,
    autopct="%1.1f%%",
    colors=["lightgreen", "orange"]
)

plt.title("Quantum Attack Outcome Distribution")
plt.show()

# -----------------------------
# 4. Encryption vs Decryption Scatter Plot
# -----------------------------
plt.figure()

for algo in data["Algorithm"].unique():
    subset = data[data["Algorithm"] == algo]

    plt.scatter(
        subset["EncryptionTime(ms)"],
        subset["DecryptionTime(ms)"],
        label=algo
    )

plt.xlabel("EncryptionTime(ms)")
plt.ylabel("DecryptionTime(ms)")
plt.title("Encryption vs Decryption Time")

plt.legend()
plt.show()

# -----------------------------
# 5. Correlation Heatmap (Clean Version)
# -----------------------------

# Select only relevant columns (microseconds only)
corr_features = data[
    [
        "KeySize",
        "KeyGenerationTime(us)",
        "EncryptionTime(us)",
        "DecryptionTime(us)",
        "CiphertextSize(bytes)"
    ]
]

correlation_matrix = corr_features.corr()

plt.figure(figsize=(8,6))
sns.heatmap(
    correlation_matrix,
    annot=True,
    cmap="coolwarm",
    fmt=".2f"
)

plt.title("Feature Correlation Heatmap")
plt.show()