import re
import sympy as sp

class NonLinearEquationError(Exception):
    pass

class Solver:
    @staticmethod
    def solve_linear_equation(equation):
        """
        Solves a single linear equation of the form ax + b = c.
        :param equation: The equation to solve in the form of a string.
        :return: A tuple containing the value of x and the steps of the solution.
        """
        equation = equation.replace(" ", "")  # Remove whitespaces
        equation = equation.lower()  # Convert to lowercase

        if '=' not in equation:
            raise ValueError("Invalid equation")

        parts = equation.split('=')
        left_side = parts[0]
        right_side = parts[1]

        if 'x' not in left_side:
            raise NonLinearEquationError

        matches = re.findall(r'([+-]?\d*)(?:\*|x)([+-]?\d*)', left_side)
        if not matches:
            raise ValueError("Invalid equation")

        a, b = matches[0]
        a, b = int(a) if a else 1, int(b) if b else 1

        c = int(right_side)

        if a == 0:
            raise ValueError("Invalid equation")

        # Solving for x
        x = (c - b) / a

        steps = [
            f"Solving {a}x + {b} = {c} for x:",
            f"1. Subtract {b} from both sides: {a}x = {c - b}",
            f"2. Divide both sides by {a}: x = {x}"
        ]

        return x, steps

    @staticmethod
    def solve_quadratic_equation(equation):
        """
        Solves a quadratic equation of the form ax^2 + bx + c = 0.
        :param equation: The equation to solve in the form of a string.
        :return: A tuple containing the roots of the quadratic equation and the steps of the solution.
        """
        equation = equation.replace(" ", "")  # Remove whitespaces
        equation = equation.lower()  # Convert to lowercase

        if '=' not in equation:
            raise ValueError("Invalid equation")

        parts = equation.split('=')
        left_side = parts[0]
        right_side = parts[1]

        if 'x' not in left_side:
            raise NonLinearEquationError

        quadratic_expr = sp.sympify(left_side) - sp.sympify(right_side)

        solutions = sp.solve(quadratic_expr, sp.symbols('x'))

        steps = [
            f"Solving {left_side} = {right_side} for x:",
            f"Solutions: {solutions}"
        ]

        return solutions, steps

    @staticmethod
    def solve_differential_equation(equation):
        """
        Solves a differential equation.
        :param equation: The differential equation to solve in the form of a string.
        :return: The solution of the differential equation and the steps of the solution.
        """
        x = sp.symbols('x')
        y = sp.Function('y')(x)
        
        # Parse the equation
        try:
            parsed_eq = sp.sympify(equation)
        except sp.SympifyError:
            raise ValueError("Invalid equation")

        # Solve the differential equation
        solution = sp.dsolve(parsed_eq, y)

        steps = [
            f"Solving differential equation {equation}:",
            f"Solution: {solution}"
        ]

        return solution, steps

    @staticmethod
    def simplify_expression(expression):
        """
        Simplifies the given expression.
        :param expression: The expression to simplify.
        :return: The simplified expression.
        """
        # Parse the expression
        try:
            parsed_expr = sp.sympify(expression)
        except sp.SympifyError:
            raise ValueError("Invalid expression")

        # Simplify the expression
        simplified_expr = sp.simplify(parsed_expr)

        return simplified_expr

if __name__ == "__main__":
    while True:
        menu_options = [
            "Linear",
            "Quadratic",
            "Differential",
            "Simplified expression",
        ]

        print("Equation:")
        for i, option in enumerate(menu_options, start=1):
            print(f"\t{i}: {option}")

        choice = input().lower()
        
        if choice in ['exit', 'e', 'quit', 'q']:
            print('Thank you')
            break

        if choice == '1':
            print('Enter an equation (e.g., "2*x + 3 = 7"): ', end=' ')

            try:
                solution, steps = Solver.solve_linear_equation(input())
                print("Solution:", solution)
                print("Steps:")
                for step in steps:
                    print(step)
            except NonLinearEquationError:
                print('You entered a non-linear equation. The current version of Kanu can only solve linear equations!')
            except ValueError as e:
                print(e)
        elif choice == '2':
            print('Enter the equation (e.g., "x**2 - 4*x + 3 = 0"): ', end=' ')

            try:
                solutions, steps = Solver.solve_quadratic_equation(input())
                print("Solutions:", solutions)
                print("Steps:")
                for step in steps:
                    print(step)
            except NonLinearEquationError:
                print('You entered a non-quadratic equation. The current version of Kanu can only solve quadratic equations!')
            except ValueError as e:
                print(e)
        elif choice == '3':
            print('Enter the differential equation (e.g., "y\' + 2*x*y = 0"): ', end=' ')
            
            # An example of a differential equation: y' + 2*x*y = 0
            try:
                solution, steps = Solver.solve_differential_equation(input())
                print("Solution:", solution)
                print("Steps:")
                for step in steps:
                    print(step)
            except ValueError as e:
                print(e)
        elif choice == '4':
            print('Enter the expression (e.g., "2*x + 3*(x - 1) - 4"): ', end=' ')
            
            # An example of an expression: 2*x + 3*(x - 1) - 4
            print(Solver.simplify_expression(input()))  # Needs implementation
        else:
            print('You must select an option from the menu!')
