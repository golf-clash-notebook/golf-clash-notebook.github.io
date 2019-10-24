FROM ruby:2.6.5

LABEL app-name="golfclashnotebook"

RUN apt-get update -qq && \
    apt-get install -y \
      build-essential \
      default-jre \
      default-jdk \
      vim \
      curl

ENV APP_HOME /var/www/app

ENV BUNDLE_PATH /bundle

# Silence warning about running bundler as root
RUN bundle config --global silence_root_warning 1

# Set the working directory
WORKDIR $APP_HOME

# Update $PATH to include working directory
ENV PATH="${PATH}:$APP_HOME"

# Install ruby dependencies
ADD Gemfile* $APP_HOME/
RUN bundle install

# COPY app to container
COPY . $APP_HOME

# Expose puma port
EXPOSE 4000
