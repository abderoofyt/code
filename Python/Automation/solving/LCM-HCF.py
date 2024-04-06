class HCF_LCM_Solver:
    @staticmethod
    def find_hcf(a, b):
        """
        Finds the Highest Common Factor (HCF) of two numbers using the Euclidean algorithm.
        """
        while b:
            a, b = b, a % b
        return a

    @staticmethod
    def find_lcm(a, b):
        """
        Finds the Least Common Multiple (LCM) of two numbers using the formula: LCM(a, b) = (a * b) / HCF(a, b)
        """
        return (a * b) // HCF_LCM_Solver.find_hcf(a, b)

    @staticmethod
    def step_by_step_hcf(a, b):
        """
        Finds the HCF of two numbers and provides step-by-step working out.
        """
        steps = []
        steps.append(f"Finding the Highest Common Factor (HCF) of {a} and {b}:")

        while b:
            steps.append(f"Dividing {a} by {b}:")
            steps.append(f"{a} = {b} * ({a // b}) + {a % b}")
            a, b = b, a % b

        steps.append(f"The Highest Common Factor (HCF) is {a}.")
        return a, steps

    @staticmethod
    def step_by_step_lcm(a, b):
        """
        Finds the LCM of two numbers and provides step-by-step working out.
        """
        hcf = HCF_LCM_Solver.find_hcf(a, b)
        lcm = (a * b) // hcf
        steps = [
            f"Finding the Least Common Multiple (LCM) of {a} and {b}:",
            f"1. Find the HCF of {a} and {b}.",
            f"   HCF({a}, {b}) = {hcf}.",
            f"2. Use the formula: LCM({a}, {b}) = (a * b) / HCF({a}, {b}).",
            f"   LCM({a}, {b}) = ({a} * {b}) / {hcf} = {lcm}."
        ]
        return lcm, steps


# Example usage:
if __name__ == "__main__":
    a = int(input("Enter the first number: "))
    b = int(input("Enter the second number: "))

    operation = input("What operation do you want to perform? (HCF or LCM): ").upper()

    if operation == "HCF":
        hcf, steps_hcf = HCF_LCM_Solver.step_by_step_hcf(a, b)
        print(f"HCF of {a} and {b}:", hcf)
        print("Steps for HCF:")
        for step in steps_hcf:
            print(step)
    elif operation == "LCM":
        lcm, steps_lcm = HCF_LCM_Solver.step_by_step_lcm(a, b)
        print(f"LCM of {a} and {b}:", lcm)
        print("Steps for LCM:")
        for step in steps_lcm:
            print(step)
    else:
        print("Invalid operation. Please choose either HCF or LCM.")
