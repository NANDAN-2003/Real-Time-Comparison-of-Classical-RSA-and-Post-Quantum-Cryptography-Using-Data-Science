import pandas as pd
import numpy as np

np.random.seed(42)

rows = []

for i in range(500):  # 500 RSA + 500 PQC = 1000 samples

    # RSA (Classical)
    rows.append({
        "Algorithm": "RSA",
        "KeySize": 2048,
        "KeyGenerationTime(us)": np.random.randint(250000, 750000),
        "EncryptionTime(us)": np.random.randint(100, 400),
        "DecryptionTime(us)": np.random.randint(2000, 5000),
        "CiphertextSize(bytes)": 256,
        "AttackResult": "BROKEN_UNDER_QUANTUM_ATTACK"
    })

    # PQC (Post Quantum)
    rows.append({
        "Algorithm": "PQC",
        "KeySize": 8192,
        "KeyGenerationTime(us)": np.random.randint(2000, 6000),
        "EncryptionTime(us)": np.random.randint(5000, 20000),
        "DecryptionTime(us)": np.random.randint(5000, 15000),
        "CiphertextSize(bytes)": np.random.choice([5468, 5472, 5476]),
        "AttackResult": "QUANTUM_SAFE"
    })

# Create DataFrame
df = pd.DataFrame(rows)

# Shuffle dataset to remove ordering bias
df = df.sample(frac=1).reset_index(drop=True)

# Save dataset
df.to_csv("result.csv", index=False)

print("Synthetic dataset generated successfully!")
print("Total rows:", len(df))