# Contributing

We want to make contributing to this project as easy and transparent as possible. All contributions
are welcome.

## Code of Conduct

The code of conduct is described in [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md).

## Issues

We use GitHub issues to track bugs, enhancements and new feature requests. Please ensure that the
issue title is clear and concise. The detailed description or body should document what needs to be
done and why. Defects should include instructions for reproducing the issue.

## Issue References

GitHub issue numbers should be referenced on the last line of the commit message. Keywords can be
used to [automatically close
issues](https://help.github.com/en/articles/closing-issues-using-keywords). For example:

```
The Subject Line

A more detailed description that explains what the change is,
why it is needed, and how it has been implemented.

Closes #123, #456
```

## Commit Messages

"Re-establishing the context of a piece of code is wasteful. We can't avoid it completely, so our
efforts should go to reducing it \[as much\] as possible. Commit messages can do exactly that and as
a result, a *commit message shows whether a developer is a good collaborator*." -- Peter Hutterer

The seven rules of a great git commit message:

1. separate subject from body with a blank line;
2. limit the subject line to 50 characters;
3. capitalise the subject line;
4. do not end the subject line with a period;
5. use the imperative mood in the subject line;
6. wrap the body at 72 characters;
7. use the body to explain _what_ and _why_ vs. _how_.

See [How to Write a Git Commit Message](http://chris.beams.io/posts/git-commit/) by Chris Beams.

## Pull Requests

We actively welcome your pull requests.

1. fork the repo and create your branch from `master`;
2. follow coding style and best practice;
3. add unit-test coverage where possible;
4. ensure the test suite passes;
5. ensure that there are no package cycles;
6. update the documentation for API changes;
7. run checkstyle and format your changes.

## Coding Style

The build will apply checkstyle and spotless checks.
Spotless rule violations can be corrected automatically as follows:

```bash
./gradlew spotlessApply
```

### Assertions

Use `assert` statements to formalise contract and document assumptions.

### Logging

Long running applications and services should log exceptional conditions and life-cycle events.
Logging in library code, where the run-time context is often unknown, is generally frowned upon and
should be kept to a minimum. Log files should be kept clean and readable; they should not be used to
trace the normal flow of execution in production code.

## License

By contributing to Platform Java, you agree that your contributions will be licensed under the
[Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0). A copy of the license is
available in the [LICENSE.md](LICENSE.md) file in the root directory of this source tree.
