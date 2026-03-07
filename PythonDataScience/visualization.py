import pandas as pd
import matplotlib.pyplot as plt

data = pd.read_csv(
    "results.csv",
    header=None,
    names=["Algorithm", "KeySize", "EncryptionTime", "AttackResult"]
)

# Bar chart: Encryption time comparison
plt.figure()
plt.bar(data["Algorithm"], data["EncryptionTime"])
plt.xlabel("Algorithm")
plt.ylabel("Encryption Time (ms)")
plt.title("RSA vs PQC Encryption Time")
plt.show()

# Pie chart: Quantum attack result
attack_counts = data["AttackResult"].value_counts()

plt.figure()
plt.pie(
    attack_counts,
    labels=attack_counts.index,
    autopct="%1.1f%%"
)
plt.title("Quantum Attack Outcome")
plt.show()
