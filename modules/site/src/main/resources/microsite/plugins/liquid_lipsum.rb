module Jekyll
  module Tags
    class LipsumTag < Liquid::Tag

      def initialize(tag_name, text, tokens)
        super
        @num_par, @min_len, @max_len = 1, 10, 30
        @starts = [ 'Nam quis nulla', 'Integer malesuada', 'In an enim', 'Sed vel lectus', 'Donec odio urna,', 'Phasellus rhoncus', 'Aenean id', 'Vestibulum fermentum', 'Pellentesque ipsum', 'Nulla non', 'Proin in tellus', 'Vivamus luctus', 'Maecenas sollicitudin', 'Etiam egestas', 'Lorem ipsum dolor sit amet,', 'Nullam feugiat,', 'Aliquam erat volutpat', 'Mauris pretium' ]
        @middle = [ 'a arcu imperdiet', 'tempus molestie,', 'porttitor ut,', 'iaculis quis,', 'metus id velit', 'lacinia neque', 'sed nisl molestie', 'sit amet nibh', 'consectetuer adipiscing', 'turpis at pulvinar vulputate,', 'erat libero tristique tellus,', 'nec bibendum odio risus', 'pretium quam', 'ullamcorper nec,', 'rutrum non,', 'nonummy ac,', 'augue id magna' ]
        @ends   = [ 'nulla.', 'malesuada.', 'lectus.', 'sem.', 'pulvinar.', 'faucibus fringilla.', 'dignissim sagittis.', 'egestas leo.', 'metus.', 'erat.', 'elit.', 'sit amet ante.', 'volutpat.', 'urna.', 'rutrum.' ]

        a,b,c = text.split
        if !c.nil?
          @num_par = a.to_i
          @min_len = b.to_i
          @max_len = c.to_i
        elsif !b.nil?
          @num_par = a.to_i
          @min_len = b.to_i
          @max_len = b.to_i
        elsif !a.nil?
          @num_par = a.to_i
        end
        if @num_par < 1 || @min_len < 1 || @max_len < 1
          raise SyntaxError.new("Syntax Error in tag 'lipsum': Bad arguments (<0)!")
        elsif @min_len > @max_len
          raise SyntaxError.new("Syntax Error in tag 'lipsum': min > max!")
        end
      end

      def render(context)
        resp = ""
        @num_par.times do |i|
          resp << render_paragraph()
        end
        resp
      end

      def render_paragraph()
        par = @starts.sample
        par << " "
        num_items = (@min_len + rand(@max_len-@min_len)).to_i
        num_items.times do |i|
          par << @middle.sample
          par << " "
        end
        par << @ends.sample
        par << "\n\n"
        return par
      end
    end
  end
end

Liquid::Template.register_tag('lipsum', Jekyll::Tags::LipsumTag)