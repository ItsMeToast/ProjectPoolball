from external_info import dynamic_potential

results = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

for i in range(10000):
    num = dynamic_potential()
    results[num-1] += 1

print(results)