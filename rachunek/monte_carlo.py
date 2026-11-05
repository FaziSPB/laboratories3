"""Hit-or-miss Monte Carlo integration (homework 1, task 1)."""

from pathlib import Path

import matplotlib.pyplot as plt
import numpy as np

SEED = 2026
N_VALUES = np.arange(50, 5001, 50)
K_VALUES = (5, 50)
OUT_DIR = Path("plots")

# Mersenne Twister
rng = np.random.Generator(np.random.MT19937(SEED))

# name -> (f, a, b, M, exact value)
PROBLEMS = {
    "int1_5_root4_x": (lambda x: 5 * x ** 0.25, 0.0, 16.0, 10.0, 128.0),
    "int2_abs_cos": (lambda x: np.abs(np.cos(x)), 0.0, np.pi, 1.0, 2.0),
    "int3_poly": (lambda x: 15 * (x - 1) ** 2 * (2 - x) ** 3, 1.0, 2.0, 0.52, 0.25),
    "ln2": (lambda x: 1 / x, 1.0, 2.0, 1.0, np.log(2)),
    "pi": (lambda x: 4 / (1 + x ** 2), 0.0, 1.0, 4.0, np.pi),
}

TITLES = {
    "int1_5_root4_x": r"$\int_0^{16} 5\sqrt[4]{x}\,dx$",
    "int2_abs_cos": r"$\int_0^{\pi} |\cos x|\,dx$",
    "int3_poly": r"$\int_1^{2} 15(x-1)^2(2-x)^3\,dx$",
    "ln2": r"$\ln 2 = \int_1^{2} \frac{1}{x}\,dx$",
    "pi": r"$\pi = \int_0^{1} \frac{4}{1+x^2}\,dx$",
}


def monte_carlo(f, a, b, M, n, k):
    """Run k independent repetitions with n points each; return k estimates."""
    x = rng.uniform(a, b, size=(k, n))
    y = rng.uniform(0, M, size=(k, n))
    C = np.count_nonzero(y <= f(x), axis=1)
    return (b - a) * M * C / n


def run_experiment(f, a, b, M, k):
    """Return array of shape (len(N_VALUES), k) with all estimates."""
    return np.array([monte_carlo(f, a, b, M, n, k) for n in N_VALUES])


def plot(name, results, exact):
    fig, axes = plt.subplots(2, 1, figsize=(8, 8), sharex=True)
    for ax, k in zip(axes, K_VALUES):
        res = results[k]
        ax.scatter(np.repeat(N_VALUES, k), res.ravel(), s=4, color="tab:blue",
                   alpha=0.5, label="single repetition")
        ax.scatter(N_VALUES, res.mean(axis=1), s=10, color="tab:red", label="mean")
        ax.axhline(exact, color="tab:green", label=f"exact value = {exact:.4f}")
        ax.set_title(f"k = {k}")
        ax.set_ylabel("estimate")
        ax.legend(loc="upper right", fontsize=8)
    axes[-1].set_xlabel("n")
    fig.suptitle(TITLES[name])
    fig.tight_layout()
    fig.savefig(OUT_DIR / f"{name}.png", dpi=150)
    plt.close(fig)


def main():
    OUT_DIR.mkdir(exist_ok=True)
    for name, (f, a, b, M, exact) in PROBLEMS.items():
        results = {k: run_experiment(f, a, b, M, k) for k in K_VALUES}
        plot(name, results, exact)


if __name__ == "__main__":
    main()
